package com.zsy.module.system.rolemenu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.rolemenu.domain.SystemRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色-菜单关联 Mapper 接口
 */
@Mapper
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenuEntity> {
    
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

} 