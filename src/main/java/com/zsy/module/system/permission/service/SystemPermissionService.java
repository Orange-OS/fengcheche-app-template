package com.zsy.module.system.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;

import java.util.List;

/**
 * 系统权限 Service 接口
 */
public interface SystemPermissionService extends IService<SystemPermissionEntity> {
    
    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SystemPermissionEntity> getPermissionsByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<SystemPermissionEntity> getPermissionsByUserId(Long userId);
    
    /**
     * 根据用户ID查询权限编码列表
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> getPermissionCodesByUserId(Long userId);
    
    /**
     * 检查权限是否已分配给角色
     * @param permissionId 权限ID
     * @return 是否已分配
     */
    boolean isPermissionAssigned(Long permissionId);
    
    /**
     * 根据菜单ID查询权限列表
     * @param menuId 菜单ID
     * @return 权限列表
     */
    List<SystemPermissionEntity> getPermissionsByMenuId(Long menuId);
    
    /**
     * 批量保存权限
     * @param permissions 权限列表
     * @return 是否成功
     */
    boolean batchSavePermissions(List<SystemPermissionEntity> permissions);

    /**
     * 更新权限状态
     * @param permissionId 权限ID
     * @param status 状态(0-禁用,1-正常)
     */
    void updatePermissionStatus(Long permissionId, Integer status);

    List<SystemPermissionEntity> getPermissionTree();
}