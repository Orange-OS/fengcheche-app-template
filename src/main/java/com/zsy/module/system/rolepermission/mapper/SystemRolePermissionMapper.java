package com.zsy.module.system.rolepermission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.rolepermission.domain.SystemRolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色-权限关联 Mapper 接口
 */
@Mapper
public interface SystemRolePermissionMapper extends BaseMapper<SystemRolePermissionEntity> {
    
    /**
     * 根据角色ID删除角色权限关联
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param("roleId") Long roleId);
} 