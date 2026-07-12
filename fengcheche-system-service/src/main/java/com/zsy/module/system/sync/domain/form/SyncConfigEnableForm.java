package com.zsy.module.system.sync.domain.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SyncConfigEnableForm {

    @NotNull(message = "启用标识不能为空")
    private Boolean enabledFlag;
}
