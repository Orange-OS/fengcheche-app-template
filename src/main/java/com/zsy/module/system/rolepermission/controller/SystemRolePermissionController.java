package com.zsy.module.system.rolepermission.controller;

import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.rolepermission.domain.vo.RolePermissionVO;
import com.zsy.module.system.rolepermission.service.SystemRolePermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色-权限关联控制器
 */
@RestController
@RequestMapping("/system/role-permission")
public class SystemRolePermissionController {
    
    @Resource
    private SystemRolePermissionService systemRolePermissionService;
    
    /**
     * 获取角色权限ID列表
     */
    @GetMapping("/list/{roleId}")
    public Result<List<Long>> getPermissionIdsByRoleId(@PathVariable Long roleId) {
        List<Long> permissionIds = systemRolePermissionService.getPermissionIdsByRoleId(roleId);
        return Result.success(permissionIds);
    }
    
    /**
     * 分配角色权限
     */
    @PostMapping("/assign")
    public Result<Void> assignRolePermissions(@RequestBody RolePermissionVO rolePermissionVO) {
        systemRolePermissionService.assignRolePermissions(rolePermissionVO.getRoleId(), rolePermissionVO.getPermissionIds());
        return Result.success();
    }
} 