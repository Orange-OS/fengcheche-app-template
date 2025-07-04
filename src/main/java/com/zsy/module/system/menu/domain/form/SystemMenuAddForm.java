package com.zsy.module.system.menu.domain.form;

import lombok.Data;

@Data
public class SystemMenuAddForm {

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
    private String createBy;

}