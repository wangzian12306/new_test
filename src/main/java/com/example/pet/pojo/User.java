package com.example.pet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String username;
    @JsonIgnore
    private String password;
    private String favorites;      // 收藏列表
    private String commentsPosted; // 发布过的评论
    private String followingList;   // 关注列表
    private String followersList;  // 粉丝列表
    private String profilePicture; // 图片
    private Integer accountStatus;  // 账号状态
    private Date createTime;
    private Date updateTime;
}