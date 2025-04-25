package com.example.pet.pojo;
import lombok.Data;
import java.util.Date;

@Data
public class Adoption {
    private Integer postId;           // 帖子ID
    private Integer userId;
    private String content;           // 文本(800字)
    private Integer budgetOrSituation;// 预算或情况
    private Integer gender;           // 性别
    private Integer age;              // 年龄
    private String occupation;       // 职业(30字内)
    private String location;         // 所在地(30字内)
    private String adoptionMethod;    // 领养方式(20字内)
    private Integer reviewStatus;     // 审核标签
    private Date createTime;
    private Date updateTime;
}