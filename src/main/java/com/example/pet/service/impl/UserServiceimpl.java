package com.example.pet.service.impl;

import com.example.pet.mapper.UserMapper;
import com.example.pet.pojo.User;
import com.example.pet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
    //加密后续再加
        userMapper.add(username,password);
    }


    @Override
    public User findByUserId(Integer userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public String findUsernameById(Integer userId) {
        return userMapper.findUsernameById(userId);
    }

    @Override
    public String findFavoritesById(Integer userId) {
        return userMapper.findFavoritesById(userId);
    }

    @Override
    public String findCommentsPostedById(Integer userId) {
        return userMapper.findCommentsPostedById(userId);
    }

    @Override
    public String findFollowingListById(Integer userId) {
        return userMapper.findFollowingListById(userId);
    }

    @Override
    public String findFollowersListById(Integer userId) {
        return userMapper.findFollowersListById(userId);
    }

    @Override
    public String findProfilePictureById(Integer userId) {
        return userMapper.findProfilePictureById(userId);
    }

    // 更新操作方法（完全对应Mapper）
    @Override
    public void updateUser(User user) {
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }

    @Override
    public void updateFavorites(Integer userId, String favorites) {
        userMapper.updateFavorites(userId,  favorites);
    }

    @Override
    public void updateCommentsPosted(Integer userId, String commentsPosted) {
        userMapper.updateCommentsPosted(userId,  commentsPosted);
    }

    @Override
    public void updateFollowingList(Integer userId, String followingList) {
        userMapper.updateFollowingList(userId,  followingList);
    }

    @Override
    public void updateFollowersList(Integer userId, String followersList) {
        userMapper.updateFollowersList(userId,  followersList);
    }

    @Override
    public void updateAccountStatus(Integer userId, Integer accountStatus) {
        userMapper.updateAccountStatus(userId,  accountStatus);
    }

    @Override
    public void updateProfilePicture(Integer userId, String profilePicture) {
        userMapper.updateProfilePicture(userId,  profilePicture);
    }


}
