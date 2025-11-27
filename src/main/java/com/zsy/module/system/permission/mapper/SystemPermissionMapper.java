package com.zsy.module.system.permission.mapper;

import com.zsy.module.system.rolemenu.domain.entity.SystemRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限 Mapper 接口
 */
@Mapper
public interface SystemPermissionMapper {

//    /**
//     * 根据角色ID查询权限列表
//     *
//     * @param roleId 角色ID
//     * @return 权限列表
//     */
//    List<SystemRoleMenuEntity> selectByRoleId(@Param("roleId") Long roleId);
//
//    /**
//     * 根据用户ID查询权限列表
//     *
//     * @param userId 用户ID
//     * @return 权限列表
//     */
//    List<SystemRoleMenuEntity> selectByUserId(@Param("userId") Long userId);
} 