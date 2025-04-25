package com.example.pet.pojo;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private Integer commentId;
    private Integer userId;
    private String content;       // 内容(100字内)
    private Integer postId;       // 对应帖子ID
    private Date createTime;      // 时间
    private Integer reviewStatus; // 审核标签
}