package com.zsy.module.system.loginlog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志
 */
@TableName("system_login_log")
@Data
@Builder
public class LoginLogEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录ip地区
     */
    private String loginIpRegion;

    /**
     * user-agent
     */
    private String userAgent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录设备
     */
    private String loginDevice;

    /**
     * 登录结果
     */
    private Integer loginResult;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)  // 插入时填充
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)  // 插入和更新时都填充
    private LocalDateTime updateTime;
}
