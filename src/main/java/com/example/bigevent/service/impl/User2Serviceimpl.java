package com.example.bigevent.service.impl;

import com.example.bigevent.mapper.User2Mapper;
import com.example.bigevent.pojo.User;
import com.example.bigevent.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User2Serviceimpl implements User2Service {
    @Autowired
    private User2Mapper user2Mapper;
    @Override
    public User findByUserName(String username) {
        User u=user2Mapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密后续再加
        user2Mapper.add(username,password);
    }
}
