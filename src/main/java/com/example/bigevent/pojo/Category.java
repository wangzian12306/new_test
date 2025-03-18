package com.example.bigevent.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id; // ID
    private String categoryName; // 分类名称
    private String categoryAlias; // 分类别名
    private Integer createUser; // 创建人ID
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
