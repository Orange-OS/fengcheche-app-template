package com.zsy.framework.config;

import cn.dev33.satoken.stp.StpInterface;
import com.zsy.module.system.permission.service.SystemPermissionService;
import com.zsy.module.system.role.service.SystemRoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    
    @Resource
    private SystemPermissionService systemPermissionService;
    
    @Resource
    private SystemRoleService systemRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 根据用户 ID 获取权限编码列表
        Long userId = Long.parseLong(loginId.toString());
        return systemPermissionService.getPermissionCodesByUserId(userId);
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 根据用户 ID 获取角色编码列表
        Long userId = Long.parseLong(loginId.toString());
        return systemRoleService.getRoleCodesByUserId(userId);
    }
} 