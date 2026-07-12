package com.zsy.module.system.region.domain.form;

import com.zsy.common.enums.StatusEnum;
import com.zsy.module.system.region.enums.RegionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SystemRegionAddUpdateForm {
    @Schema(name = "地域代码(行政区划代码)")
    @NotBlank(message = "地域代码不能为空")
    private String code;

    @Schema(name = "地域名称")
    @NotBlank(message = "地域名称不能为空")
    private String name;

    @Schema(name = "上级地域代码")
    @NotBlank(message = "上级地域代码不能为空")
    private String parent;

    @Schema(name = "地域类型(1-省 2-市 3-区县 4-乡镇 5-村/社区)")
    @NotNull(message = "地域类型不能为空")
    private RegionTypeEnum type;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "状态(0-禁用 1-启用)")
    @NotNull(message = "状态不能为空")
    private StatusEnum status;
}
