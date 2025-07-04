package com.zsy.module.system.rolemenu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.rolemenu.domain.SystemRoleMenuEntity;

import java.util.List;

/**
 * 角色-菜单关联 Service 接口
 */
public interface SystemRoleMenuService extends IService<SystemRoleMenuEntity> {
    
    /**
     * 根据角色ID获取菜单ID列表
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);
    
    /**
     * 分配角色菜单
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 是否成功
     */
    boolean assignRoleMenus(Long roleId, List<Long> menuIds);
    
    /**
     * 根据角色ID删除角色菜单关联
     * @param roleId 角色ID
     * @return 是否成功
     */
    boolean deleteByRoleId(Long roleId);
    
    /**
     * 根据菜单ID删除角色菜单关联
     * @param menuId 菜单ID
     * @return 是否成功
     */
    boolean deleteByMenuId(Long menuId);
} 