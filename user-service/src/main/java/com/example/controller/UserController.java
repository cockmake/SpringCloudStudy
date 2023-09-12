package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mapper.UserMapper;
import entity.user.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Validated
@Controller
public class UserController {
    @Resource
    UserMapper userMapper;

    @ResponseBody
    @PostMapping("/api/user/login")
    public User UserLogin(@RequestBody User user){
        System.out.println(user);
        QueryWrapper<User> userQueryWrapper = Wrappers
                .<User>query()
                .eq("username", user.username)
                .eq("password", user.password);
        try{
            return userMapper.selectOne(userQueryWrapper);
        }catch (Exception e){
            return new User();
        }
    }
}
