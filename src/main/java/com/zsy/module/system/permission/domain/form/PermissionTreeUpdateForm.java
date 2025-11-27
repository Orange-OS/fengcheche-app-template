package com.zsy.module.system.permission.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PermissionTreeUpdateForm {

    @Schema(description = "权限ID列表")
    @NotEmpty(message = "权限ID列表不能为空")
    private List<Long> permissionIdList;

}
