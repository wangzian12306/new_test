CREATE TABLE `foster_order` (
                                `order_id` VARCHAR(36) NOT NULL COMMENT '订单编号（UUID）',
                                `order_date` DATE NOT NULL COMMENT '订单创建日期',
                                `start_time` DATETIME NOT NULL COMMENT '寄养开始时间',
                                `end_time` DATETIME NOT NULL COMMENT '寄养结束时间',

                                `foster_days` INT UNSIGNED GENERATED ALWAYS AS (
    DATEDIFF(end_time, start_time)
  ) STORED COMMENT '自动计算寄养天数',

                                `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
                                `address` VARCHAR(255) NOT NULL COMMENT '详细地址',

                                `foster_mode` ENUM('HOME_STAY','PET_HOTEL','HOSPITAL') NOT NULL
    COMMENT '寄养方式（家庭寄养/宠物酒店/医疗寄养）',

                                `pet_type` ENUM('DOG','CAT','BIRD','REPTILE','OTHER') NOT NULL
    COMMENT '宠物类型',

                                `creator_id` VARCHAR(36) NOT NULL COMMENT '发起人ID',
                                `acceptor_id` VARCHAR(36) DEFAULT NULL COMMENT '接单人ID',

                                `status` ENUM('PENDING','ACCEPTED','IN_PROGRESS','COMPLETED','CANCELLED')
    NOT NULL DEFAULT 'PENDING' COMMENT '订单状态',

                                `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                    ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                PRIMARY KEY (`order_id`),
                                INDEX `idx_creator` (`creator_id`),
                                INDEX `idx_acceptor` (`acceptor_id`),
                                INDEX `idx_time_range` (`start_time`, `end_time`),
                                INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='宠物寄养订单表';