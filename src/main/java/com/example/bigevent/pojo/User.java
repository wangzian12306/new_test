package com.example.bigevent.pojo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class User {
    private Integer id; // ID
    private String username; // 用户名
    @JsonIgnore
    private String password; // 密码
    private String nickname; // 昵称
    private String email; // 邮箱
    private String userPic; // 头像
    private Date createTime; // 创建时间
    private Date updateTime; // 修改时间

}
