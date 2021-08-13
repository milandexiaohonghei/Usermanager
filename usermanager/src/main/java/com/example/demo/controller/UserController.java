package com.example.demo.controller;

import com.example.demo.config.AppFinal;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录功能
     */
    @RequestMapping("/login")
    @ResponseBody // 当前方法返回的为数据而非视图
    public Object login(String username, String password,
                        HttpServletRequest request) throws JsonProcessingException {
        User user = userMapper.login(username, password);
        if (user != null) {
            // 登录成功，设置
            HttpSession session = request.getSession();
            // 存放到 Session
            session.setAttribute(AppFinal.USERINFO_SESSIONKEY, user);
        } else {
            user = new User();
        }
        return user;
    }

    /**
     * 登录功能
     */
    @RequestMapping("/reg")
    @ResponseBody // 当前方法返回的为数据而非视图
    public int register(User user) {
        return userMapper.register(user);
    }

    /**
     * 查询用户信息
     */
    @RequestMapping("/getlist")
    public Object getList(int cpage,
                          int psize,
                          String name,
                          String address,
                          String email,
                          HttpServletRequest request) {
        name = name.equals("") ? null : name;
        address = address.equals("") ? null : address;
        email = email.equals("") ? null : email;
        Integer isadmin = null;
        // session 中获取是否为管理员
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(AppFinal.USERINFO_SESSIONKEY) != null) {
            User sessUser = (User) session.getAttribute(AppFinal.USERINFO_SESSIONKEY);
            if (sessUser.getIsadmin() == 0) {
                isadmin = 0;
            }
        }
        int sindex = (cpage - 1) * psize;
        // 查询的当月数据
        List<User> list = userMapper.getUserByPage(sindex, psize, name, address, email, isadmin);
        // 查询总条数
        int totalCount = userMapper.getUserCount(name, address, email, isadmin);
        // 分页总页数
        int tpage = (int) Math.ceil(totalCount / (psize * 1.0));
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("tpage", tpage);
        map.put("tcount", totalCount);
        return map;
    }

    /**
     * 删除用户信息
     */
    @RequestMapping("/del")
    public int del(String ids) {
        int result = 0;
        if (ids != null && !ids.equals("")) {
            if (ids.contains(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] idArr = ids.split(",");
            result = userMapper.del(idArr);
        }
        return result;
    }

    /**
     * 根据 id 查询文章
     */
    @RequestMapping("/getuser")
    public User getUserById(@RequestParam Integer id) {
        return userMapper.getUserById(id);
    }

    /**
     * 修改操作
     */
    @RequestMapping("/update")
    public int update(User user) {
        return userMapper.update(user);
    }

    /**
     * 添加操作
     */
    @RequestMapping("/add")
    public int add(User user) {
        return userMapper.add(user);
    }

    /**
     * 是否超管
     */
    @RequestMapping("/isadmin")
    public int isAdmin(HttpServletRequest request) {
        // session 中获取是否为管理员
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(AppFinal.USERINFO_SESSIONKEY) != null) {
            User sessUser = (User) session.getAttribute(AppFinal.USERINFO_SESSIONKEY);
            if (sessUser.getIsadmin() == 1) {
                return 1;
            }
        }
        return 0;
    }


}
