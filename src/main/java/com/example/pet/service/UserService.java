package com.example.pet.service;

import com.example.pet.pojo.User;

public interface UserService {


    // 注册登录
    void register(String username, String password);

    User findByUserName(String username);
    User findByUserId(Integer userId);
    String findUsernameById(Integer userId);
    String findFavoritesById(Integer userId);
    String findCommentsPostedById(Integer userId);
    String findFollowingListById(Integer userId);
    String findFollowersListById(Integer userId);
    String findProfilePictureById(Integer userId);

    void updateUser(User user);
    void updateFavorites(Integer userId, String favorites);
    void updateCommentsPosted(Integer userId, String commentsPosted);
    void updateFollowingList(Integer userId, String followingList);
    void updateFollowersList(Integer userId, String followersList);
    void updateAccountStatus(Integer userId, Integer accountStatus);
    void updateProfilePicture(Integer userId, String profilePicture);

}
