package com.example.bigevent.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 宠物寄养订单实体类
 * 创建时间：2025-04-01 22:30
 */
@Data
public class FosterOrder {
    // 核心订单信息
    private String orderId;              // 订单编号（建议UUID）
    private LocalDate orderDate;         // 订单创建日期
    private LocalDateTime startTime;     // 寄养开始时间（精确到时分秒）
    private LocalDateTime endTime;       // 寄养结束时间

    // 业务计算字段
    private Integer fosterDays;          // 自动计算的寄养天数
    private BigDecimal totalAmount;      // 总金额（建议DECIMAL类型防精度丢失）

    // 服务信息
    private String address;              // 寄养地址（可细化到省市县）
    private FosterMode fosterMode;       // 寄养方式枚举
    private PetType petType;             // 宠物类型枚举

    // 用户关联
    private String creatorId;            // 订单发起人ID（关联用户表）
    private String acceptorId;           // 接单人ID（允许NULL）

    // 状态控制
    private OrderStatus status;          // 复合状态枚举

    // 自动填充字段
    private LocalDateTime createTime;    // 记录创建时间
    private LocalDateTime updateTime;    // 最后更新时间

    /**
     * 寄养方式枚举
     */
    public enum FosterMode {
        HOME_STAY("家庭寄养"),
        PET_HOTEL("宠物酒店"),
        HOSPITAL("医疗寄养");

        private final String desc;
        FosterMode(String desc) { this.desc  = desc; }
    }

    /**
     * 宠物类型枚举
     */
    public enum PetType {
        DOG, CAT, BIRD, REPTILE, OTHER
    }

    /**
     * 订单状态机设计
     */
    public enum OrderStatus {
        PENDING("待接单"),
        ACCEPTED("已接单"),
        IN_PROGRESS("服务中"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String desc;
        OrderStatus(String desc) { this.desc  = desc; }
    }
}