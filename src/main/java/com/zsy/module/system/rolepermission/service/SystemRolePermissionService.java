package com.zsy.module.system.rolepermission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.rolepermission.domain.SystemRolePermissionEntity;

import java.util.List;

/**
 * 角色-权限关联 Service 接口
 */
public interface SystemRolePermissionService extends IService<SystemRolePermissionEntity> {
    
    /**
     * 根据角色ID获取权限ID列表
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getPermissionIdsByRoleId(Long roleId);
    
    /**
     * 分配角色权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 是否成功
     */
    boolean assignRolePermissions(Long roleId, List<Long> permissionIds);
    
    /**
     * 根据角色ID删除角色权限关联
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean deleteByRoleId(Long roleId);
    
    /**
     * 根据权限ID删除角色权限关联
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean deleteByPermissionId(Long permissionId);
} 