package com.zsy.module.system.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单 Mapper 接口
 */
@Mapper
public interface SystemMenuMapper extends BaseMapper<SystemMenuEntity> {
    
    /**
     * 根据角色ID查询菜单列表
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SystemMenuEntity> selectByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据用户ID查询菜单列表
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SystemMenuEntity> selectByUserId(@Param("userId") Long userId);
}
