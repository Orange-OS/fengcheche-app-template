package com.zsy.module.system.job.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 定时任务 更新
 */
@Data
public class SmartJobUpdateForm extends SmartJobAddForm {

    @Schema(description = "任务id")
    @NotNull(message = "任务id不能为空")
    private Integer jobId;
}
