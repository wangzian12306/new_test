package com.example.bigevent.service;

import com.example.bigevent.pojo.User;

public interface User2Service {
    //用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);

    void update(User user);
}
