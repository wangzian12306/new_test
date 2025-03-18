package com.example.bigevent.service;

import com.example.bigevent.pojo.User;

public interface UserService {
    //用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);
}
