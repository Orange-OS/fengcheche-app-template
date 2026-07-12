package com.zsy.module.system.menu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.domain.form.SystemMenuAddForm;
import com.zsy.module.system.menu.domain.form.SystemMenuUpdateForm;

import java.util.List;

/**
 * 系统菜单 Service 接口
 */
public interface SystemMenuService extends IService<SystemMenuEntity> {

    /**
     * 根据角色ID查询菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SystemMenuEntity> getMenusByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SystemMenuEntity> getMenusByUserId(Long userId);

    /**
     * 构建菜单树
     *
     * @param menus 菜单列表
     * @return 菜单树
     */
    List<SystemMenuEntity> buildMenuTree(List<SystemMenuEntity> menus);

    /**
     * 根据用户ID查询菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<SystemMenuEntity> getMenuTreeByUserId(Long userId);

    Page<SystemMenuEntity> page(Integer current, Integer size, SystemMenuEntity menu);

    void create(SystemMenuAddForm addForm);

    void update(SystemMenuUpdateForm updateForm);

    void delete(Long id);

    void updateMenuStatus(Long id, Integer status);

}
