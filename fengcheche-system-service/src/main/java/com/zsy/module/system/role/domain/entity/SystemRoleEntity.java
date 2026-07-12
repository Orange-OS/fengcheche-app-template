package com.zsy.module.system.role.domain.entity;

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
 * 系统角色表
 */
@TableName(value ="system_roles")
@Data
@ToString
@EqualsAndHashCode
public class SystemRoleEntity implements Serializable {
    /**
     * 角色 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态(0-禁用,1-正常)
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

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