package com.zsy.module.system.region.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "地域信息")
public class SystemRegionVO {
    @Schema(description = "地域代码(行政区划代码)")
    private String code;

    @Schema(description = "地域名称")
    private String name;

    @Schema(description = "上级地域代码")
    private String parent;

    @Schema(description = "地域类型(1-省 2-市 3-区县 4-乡镇 5-村/社区)")
    private Integer type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0-禁用 1-启用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
