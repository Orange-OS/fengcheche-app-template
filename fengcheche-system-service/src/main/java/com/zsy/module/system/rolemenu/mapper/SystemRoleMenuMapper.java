package com.zsy.module.system.rolemenu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.rolemenu.domain.entity.SystemRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色-菜单关联 Mapper 接口
 */
@Mapper
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenuEntity> {

    /**
     * 查询用户ID关联权限
     * @param userId 角色ID
     * @return 影响行数
     */
    List<SystemMenuEntity> selectListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID删除角色菜单关联
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据菜单ID删除角色菜单关联
     * @param menuId 菜单ID
     * @return 影响行数
     */
    int deleteByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据角色ID查询选择的菜单权限
     *
     */
    List<SystemMenuEntity> queryPermissionsByUserId(@Param("userId") Long userId);

    List<Long> queryPermissionIdsByRoleId(@Param("roleId") Long roleId);
}