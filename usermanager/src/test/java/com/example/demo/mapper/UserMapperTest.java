package com.example.demo.mapper;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Transactional
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void login() {
    }

    @Test
    void register() {
    }

    @Test
    void del() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserByPage() {
        // 每页显示条数
        int psize = 2;
        // 当前页面
        int cpage = 2;
        // 计算开始查询下标
        int sindex = (cpage - 1) * psize;
        List<User> list = userMapper.getUserByPage(sindex, psize, null, null, null, 0);
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        String ids = "1,2,3,";
        String[] idArr = ids.split(",");
        for (String item : idArr) {
            System.out.println(item);
        }

    }
}