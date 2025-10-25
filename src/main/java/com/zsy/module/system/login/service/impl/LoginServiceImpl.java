package com.zsy.module.system.login.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zsy.framework.common.exception.BusinessException;
import com.zsy.module.system.login.domain.LoginResultVO;
import com.zsy.module.system.login.domain.SystemUserLoginForm;
import com.zsy.module.system.login.service.LoginService;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.service.SystemMenuService;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;
import com.zsy.module.system.permission.service.SystemPermissionService;
import com.zsy.module.system.user.domain.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录服务实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemMenuService systemMenuService;

    @Resource
    private SystemPermissionService systemPermissionService;

    @Override
    public LoginResultVO login(SystemUserLoginForm systemUserLoginForm) {
        String username = systemUserLoginForm.getUsername();
        String password = systemUserLoginForm.getPassword();

        // 查询用户
        SystemUserEntity user = systemUserService.selectByUsername(username);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        // 密码校验
        String encryptPassword = SaSecureUtil.md5(password);
        if (!user.getPassword().equals(encryptPassword)) {
            throw BusinessException.of("密码错误");
        }

        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw BusinessException.of("用户已被禁用");
        }

        // 更新登录信息
        user.setLastLoginTime(LocalDateTime.now());
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 设置登录 IP
        user.setLoginIp(ServletUtil.getClientIP(request));
        systemUserService.updateById(user);

        // 登录
        StpUtil.login(user.getId());

        // 返回 token 和用户信息
        LoginResultVO loginResultVO = new LoginResultVO();
        loginResultVO.setToken(StpUtil.getTokenValue());

        // 清除敏感信息
        user.setPassword("***");
        loginResultVO.setUserInfo(user);

        return loginResultVO;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public SystemUserEntity getUserInfo() {
        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        SystemUserEntity user = systemUserService.selectById(Long.parseLong(loginId.toString()));

        // 清除敏感信息
        if (user != null) {
            user.setPassword(null);
        }

        return user;
    }

    @Override
    public List<SystemMenuEntity> getUserMenuList() {

        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        if (StpUtil.hasRole("admin")) {
            userId = null;
        }

        // 获取用户菜单树
        return systemMenuService.getMenuTreeByUserId(userId);
    }

    @Override
    public List<SystemPermissionEntity> getUserPermissionList() {
        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        // 获取用户权限列表
        return systemPermissionService.getPermissionsByUserId(userId);
    }

    public static void main(String[] args) {
        System.out.println(SaSecureUtil.md5("123456"));
    }
} 