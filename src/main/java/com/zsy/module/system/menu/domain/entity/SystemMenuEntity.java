package com.zsy.module.system.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统菜单表
 */
@TableName(value ="system_menus")
@Data
@ToString
@EqualsAndHashCode
public class SystemMenuEntity implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链(0-否,1-是)
     */
    private Integer isFrame;

    /**
     * 是否缓存(0-不缓存,1-缓存)
     */
    private Integer isCache;

    /**
     * 菜单类型(M-目录,C-菜单,F-按钮)
     */
    private String menuType;

    /**
     * 菜单状态(0-禁用,1-正常)
     */
    private Integer status;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否显示(0-不显示,1-显示)
     */
    private Integer visible;

    /**
     * 备注
     */
    private String remark;

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
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 子菜单列表
     */
    @TableField(exist = false)
    private List<SystemMenuEntity> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}