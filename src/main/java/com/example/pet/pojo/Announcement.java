package com.example.pet.pojo;
import lombok.Data;
import java.util.Date;

@Data
public class Announcement {
    private Integer announcementId;
    private String title;          // 标题(30字)
    private String tag;            // 标签(10字)
    private String content;        // 内容(800字)
    private Date createTime;       // 时间
    private Date updateTime;
}