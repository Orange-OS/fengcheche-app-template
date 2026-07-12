package com.zsy.module.system.config.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SystemConfigAddForm {

    @Schema(name = "参数键")
    @NotEmpty(message = "参数键不能为空")
    private String configKey;

    @Schema(name = "参数名称")
    @NotEmpty(message = "参数名称不能为空")
    private String configName;

    @Schema(name = "参数值")
    @NotEmpty(message = "参数值不能为空")
    private String configValue;

    @Schema(name = "备注")
    private String remark;
}
