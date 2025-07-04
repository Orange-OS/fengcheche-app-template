package com.zsy.module.system.rolepermission.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限视图对象
 */
@Data
public class RolePermissionVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
    
    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;
} 