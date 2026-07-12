package com.zsy.module.system.userrole.domain.vo;

import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SystemUserRoleVO extends SystemUserEntity {

    @Schema(description = "角色ID列表")
    private List<Long> roleIds;
}
