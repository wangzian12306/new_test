package com.example.bigevent.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FosterOrder {
    private String orderId;          // UUID主键
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer fosterDays;      // 自动计算
    private BigDecimal totalAmount;
    private String address;
    private String fosterMode;       // 对应枚举值
    private String petType;          // 对应枚举值
    private Integer creatorId;       // 通过ThreadLocal获取
    private Integer acceptorId;      // 可空
    private String status;           // 默认PENDING

    public enum FosterMode {
        HOME_STAY, PET_HOTEL, HOSPITAL
    }

    public enum PetType {
        DOG, CAT, BIRD, REPTILE, OTHER
    }

    public enum Status {
        PENDING, ACCEPTED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}