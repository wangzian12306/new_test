package com.example.pet.pojo;
import lombok.Data;
import java.util.Date;

@Data
public class Fostering {
    private Integer postId;        // 帖子ID
    private Integer userId;
    private String content;        // 文本(800字)
    private Integer budget;        // 预算
    private String images;         // 图片
    private String adoptionMethod; // 领养方式(20字内)
    private Integer reviewStatus;  // 审核标签
    private Date createTime;
    private Date updateTime;
}