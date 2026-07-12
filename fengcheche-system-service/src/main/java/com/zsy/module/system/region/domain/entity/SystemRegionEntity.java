package com.zsy.module.system.region.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地域信息表
 */
@TableName(value ="system_region")
@Data
@ToString
@EqualsAndHashCode
public class SystemRegionEntity implements Serializable {

    /**
     * 地域代码(行政区划代码)
     */
    @TableId(type = IdType.INPUT)
    private String code;

    /**
     * 地域名称
     */
    private String name;

    /**
     * 上级地域代码
     */
    private String parent;

    /**
     * 地域类型(1-省 2-市 3-区县 4-乡镇 5-村/社区)
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0-禁用 1-启用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}