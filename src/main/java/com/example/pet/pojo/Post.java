package com.example.pet.pojo;
import lombok.Data;
import java.util.Date;

@Data
public class Post {
    private Integer postId;
    private Integer postType;     // 帖子类型
    private String title;         // 标题(30字以内)
    private Integer userId;       // 用户ID
    private Integer likes;        // 点赞量
    private String content;       // 文本(超长字符串)
    private String images;        // 图片
    private Date createTime;      // 时间
    private Integer reviewStatus; // 审核标签
}