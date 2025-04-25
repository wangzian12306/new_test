package com.example.pet.service;

import com.example.pet.pojo.User;

public interface UserService {
    //用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);

    void update(User user);
}
