package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public User login(String username, String password);

    public int register(User user);

    public int del(String[] ids);

    // 查询接口（无分页）
    public List<User> getUser(String name,
                              String address,
                              String email);

    // 查询所有用户，有分页
    public List<User> getUserByPage(int sindex,
                                    int psize,
                                    String name,
                                    String address,
                                    String email,
                                    Integer isadmin);

    // 查询所有用户的条数
    public int getUserCount(String name,
                            String address,
                            String email,
                            Integer isadmin);

    User getUserById(Integer id);

    int update(User user);

    int add(User user);
}
