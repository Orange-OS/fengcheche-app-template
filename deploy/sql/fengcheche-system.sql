-- 系统服务库：用户、角色、菜单、权限、配置、日志、心跳、同步配置等系统域数据。
CREATE DATABASE IF NOT EXISTS `fengcheche_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `fengcheche_system`;

CREATE TABLE IF NOT EXISTS `system_users` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(64) NOT NULL COMMENT '用户账号',
    `password` varchar(255) NOT NULL COMMENT '密码',
    `avatar` varchar(500) DEFAULT NULL COMMENT '用户头像',
    `nickname` varchar(100) DEFAULT NULL COMMENT '用户昵称',
    `sex` int DEFAULT NULL COMMENT '用户性别',
    `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
    `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
    `region_code` varchar(32) DEFAULT NULL COMMENT '所属地域',
    `dept_id` bigint DEFAULT NULL COMMENT '所属部门',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `status` int NOT NULL DEFAULT 1 COMMENT '账号状态(1-正常 0-停用)',
    `login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录IP',
    `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_users_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

CREATE TABLE IF NOT EXISTS `system_roles` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name` varchar(100) NOT NULL COMMENT '角色名称',
    `code` varchar(100) NOT NULL COMMENT '角色编码',
    `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
    `status` int NOT NULL DEFAULT 1 COMMENT '角色状态(0-禁用,1-正常)',
    `sort` int DEFAULT 0 COMMENT '排序',
    `create_by` bigint DEFAULT NULL COMMENT '创建者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '更新者',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_roles_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

CREATE TABLE IF NOT EXISTS `system_menus` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name` varchar(255) NOT NULL COMMENT '菜单名称',
    `parent_id` bigint DEFAULT 0 COMMENT '父菜单ID',
    `sort` int DEFAULT 0 COMMENT '显示顺序',
    `path` varchar(255) DEFAULT NULL COMMENT '路由地址',
    `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
    `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
    `is_frame` int DEFAULT 0 COMMENT '是否为外链(0-否,1-是)',
    `is_cache` int DEFAULT 1 COMMENT '是否缓存(0-不缓存,1-缓存)',
    `menu_type` varchar(10) NOT NULL COMMENT '菜单类型(M-目录,C-菜单,F-按钮)',
    `status` int NOT NULL DEFAULT 1 COMMENT '菜单状态(0-禁用,1-正常)',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
    `visible` int DEFAULT 1 COMMENT '是否显示(0-不显示,1-显示)',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_by` bigint DEFAULT NULL COMMENT '创建者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '更新者',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_system_menus_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

CREATE TABLE IF NOT EXISTS `system_user_roles` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_user_roles_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

CREATE TABLE IF NOT EXISTS `system_role_menus` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_role_menus_role_menu` (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单关联表';

CREATE TABLE IF NOT EXISTS `system_role_permissions` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_role_permissions_role_menu` (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

CREATE TABLE IF NOT EXISTS `system_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `config_key` varchar(100) NOT NULL COMMENT '参数键',
    `config_name` varchar(100) NOT NULL COMMENT '参数名称',
    `config_value` varchar(1000) DEFAULT NULL COMMENT '参数值',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_system_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

CREATE TABLE IF NOT EXISTS `system_region` (
    `code` varchar(32) NOT NULL COMMENT '地域代码',
    `name` varchar(100) NOT NULL COMMENT '地域名称',
    `parent` varchar(32) DEFAULT NULL COMMENT '上级地域代码',
    `type` int DEFAULT NULL COMMENT '地域类型',
    `sort` int DEFAULT 0 COMMENT '排序',
    `status` int DEFAULT 1 COMMENT '状态(0-禁用 1-启用)',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`code`),
    KEY `idx_system_region_parent` (`parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地域信息表';

CREATE TABLE IF NOT EXISTS `system_chat_message` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `from_user_id` bigint NOT NULL COMMENT '发送方用户ID',
    `to_user_id` bigint NOT NULL COMMENT '接收方用户ID',
    `content` text NOT NULL COMMENT '消息内容',
    `read_flag` int NOT NULL DEFAULT 0 COMMENT '是否已读(0-未读 1-已读)',
    `send_time` datetime NOT NULL COMMENT '发送时间',
    PRIMARY KEY (`id`),
    KEY `idx_system_chat_from_to_time` (`from_user_id`, `to_user_id`, `send_time`),
    KEY `idx_system_chat_to_read` (`to_user_id`, `read_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户聊天消息表';

CREATE TABLE IF NOT EXISTS `system_login_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint DEFAULT NULL COMMENT '用户ID',
    `user_type` int DEFAULT NULL COMMENT '用户类型',
    `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
    `login_ip` varchar(64) DEFAULT NULL COMMENT '登录IP',
    `login_ip_region` varchar(255) DEFAULT NULL COMMENT '登录IP地区',
    `user_agent` varchar(1000) DEFAULT NULL COMMENT 'User-Agent',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `login_device` varchar(100) DEFAULT NULL COMMENT '登录设备',
    `login_result` int DEFAULT NULL COMMENT '登录结果',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_system_login_log_user_id` (`user_id`),
    KEY `idx_system_login_log_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

CREATE TABLE IF NOT EXISTS `system_operation_logs` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `module` varchar(100) DEFAULT NULL COMMENT '操作模块',
    `type` varchar(100) DEFAULT NULL COMMENT '操作类型',
    `description` varchar(500) DEFAULT NULL COMMENT '操作描述',
    `method` varchar(20) DEFAULT NULL COMMENT '请求方法',
    `url` varchar(500) DEFAULT NULL COMMENT '请求URL',
    `ip` varchar(64) DEFAULT NULL COMMENT '请求IP',
    `user_id` bigint DEFAULT NULL COMMENT '操作人ID',
    `username` varchar(100) DEFAULT NULL COMMENT '操作人用户名',
    `params` text COMMENT '请求参数',
    `result` text COMMENT '返回结果',
    `status` int DEFAULT NULL COMMENT '操作状态(0-失败,1-成功)',
    `error_msg` text COMMENT '错误信息',
    `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_system_operation_logs_user_id` (`user_id`),
    KEY `idx_system_operation_logs_operate_time` (`operate_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

CREATE TABLE IF NOT EXISTS `system_heartbeat_record` (
    `heartbeat_record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_path` varchar(500) DEFAULT NULL COMMENT '项目路径',
    `server_ip` varchar(64) DEFAULT NULL COMMENT '服务器IP',
    `process_no` int DEFAULT NULL COMMENT '进程号',
    `process_start_time` datetime DEFAULT NULL COMMENT '进程开启时间',
    `heartbeat_time` datetime DEFAULT NULL COMMENT '心跳当前时间',
    PRIMARY KEY (`heartbeat_record_id`),
    KEY `idx_system_heartbeat_time` (`heartbeat_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统心跳记录表';

CREATE TABLE IF NOT EXISTS `system_sync_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `sync_name` varchar(100) NOT NULL COMMENT '同步名称',
    `source_table` varchar(100) NOT NULL COMMENT '源表',
    `target_table` varchar(100) NOT NULL COMMENT '目标表',
    `primary_key_column` varchar(100) NOT NULL COMMENT '主键列',
    `version_column` varchar(100) DEFAULT NULL COMMENT '版本列',
    `delete_flag_column` varchar(100) DEFAULT NULL COMMENT '删除标记列',
    `delete_flag_deleted_value` varchar(100) DEFAULT NULL COMMENT '删除标记已删除值',
    `column_list` text COMMENT '同步列清单',
    `cron` varchar(100) DEFAULT NULL COMMENT 'Cron表达式',
    `batch_size` int DEFAULT 1000 COMMENT '批量大小',
    `last_synced_version` varchar(100) DEFAULT NULL COMMENT '最后同步版本',
    `last_synced_pk` varchar(100) DEFAULT NULL COMMENT '最后同步主键',
    `enabled_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `deleted_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统同步配置表';

