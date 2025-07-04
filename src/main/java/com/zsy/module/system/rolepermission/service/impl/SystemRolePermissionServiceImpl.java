package com.zsy.module.system.rolepermission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.rolepermission.domain.SystemRolePermissionEntity;
import com.zsy.module.system.rolepermission.mapper.SystemRolePermissionMapper;
import com.zsy.module.system.rolepermission.service.SystemRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统权限 Service 实现类
 */
@Service
public class SystemRolePermissionServiceImpl extends ServiceImpl<SystemRolePermissionMapper, SystemRolePermissionEntity> implements SystemRolePermissionService {

    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
        return null;
    }

    @Override
    public boolean assignRolePermissions(Long roleId, List<Long> permissionIds) {
        return false;
    }

    @Override
    public boolean deleteByRoleId(Long roleId) {
        return false;
    }

    @Override
    public boolean deleteByPermissionId(Long permissionId) {
        return false;
    }
}