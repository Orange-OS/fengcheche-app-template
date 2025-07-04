package com.zsy.module.system.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限 Mapper 接口
 */
@Mapper
public interface SystemPermissionMapper extends BaseMapper<SystemPermissionEntity> {
    
    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SystemPermissionEntity> selectByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<SystemPermissionEntity> selectByUserId(@Param("userId") Long userId);
} 