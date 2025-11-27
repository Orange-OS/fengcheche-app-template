package com.zsy.module.system.rolepermission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.rolepermission.domain.entity.SystemRolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-权限关联 Mapper 接口
 */
@Mapper
public interface SystemRolePermissionMapper extends BaseMapper<SystemRolePermissionEntity> {

    void deleteByRoleId(Long roleId);
}