package com.zsy.module.system.rolepermission.service;

import com.zsy.module.system.menu.mapper.SystemMenuMapper;
import com.zsy.module.system.rolemenu.mapper.SystemRoleMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统权限 Service 实现类
 */
@Service
public class SystemRolePermissionServiceImpl implements SystemRolePermissionService {

    @Resource
    private SystemMenuMapper systemMenuMapper;


    @Override
    public List<String> getPermissionCodesByUserId(Long userId) {
        return null;
    }

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