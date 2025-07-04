CREATE TABLE system_menu
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,                              -- 菜单ID
    name       VARCHAR(255) NOT NULL,                                          -- 菜单名称
    path       VARCHAR(255),                                                   -- 菜单路径
    icon       VARCHAR(255),                                                   -- 菜单图标
    parent_id  BIGINT,                                                         -- 父菜单ID
    sort_order INT       DEFAULT 0,                                            -- 排序字段
    type       VARCHAR(50)  NOT NULL,                                          -- 菜单类型（如目录、菜单、按钮）
    status     VARCHAR(50)  NOT NULL,                                          -- 菜单状态（如启用、禁用）
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);

-- 创建用药记录表
CREATE TABLE `life_medication_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_name` varchar(100) NOT NULL COMMENT '药品名称',
  `medication_time` datetime NOT NULL COMMENT '用药时间',
  `notes` varchar(500) COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) COMMENT '更新人',
  `updated_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用药记录表';