package com.zsy.module.system.sync.domain.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SyncConfigUpdateForm extends SyncConfigAddForm {

    @NotNull(message = "ID不能为空")
    private Long id;
}
