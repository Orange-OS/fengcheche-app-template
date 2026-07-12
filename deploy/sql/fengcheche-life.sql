-- 生活服务库：生活域独立业务数据。
CREATE DATABASE IF NOT EXISTS `fengcheche_life` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `fengcheche_life`;

CREATE TABLE IF NOT EXISTS `life_medication_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `medicine_name` varchar(100) NOT NULL COMMENT '药品名称',
    `medication_time` date NOT NULL COMMENT '用药日期',
    `notes` varchar(500) DEFAULT NULL COMMENT '备注',
    `created_by` bigint NOT NULL COMMENT '创建人',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_life_medication_record_created_by` (`created_by`),
    KEY `idx_life_medication_record_medication_time` (`medication_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用药记录表';

