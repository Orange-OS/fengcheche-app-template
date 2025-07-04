package com.zsy.module.system.permission.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.FieldFill;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统权限表
 */
@TableName(value ="system_permissions")
@Data
@ToString
@EqualsAndHashCode
public class SystemPermissionEntity implements Serializable {
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 权限类型(menu-菜单,button-按钮,api-接口)
     */
    private String type;

    /**
     * 权限描述
     */
    private String description;
    
    /**
     * 关联菜单ID
     */
    private Long menuId;
    
    /**
     * 状态(0-禁用,1-正常)
     */
    private Integer status;
    
    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
} 