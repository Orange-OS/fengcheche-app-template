package com.zsy.module.system.log.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志表
 */
@TableName(value ="system_operation_logs")
@Data
@ToString
@EqualsAndHashCode
public class SystemOperationLogEntity implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作人用户名
     */
    private String username;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 操作状态(0-失败,1-成功)
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
} 