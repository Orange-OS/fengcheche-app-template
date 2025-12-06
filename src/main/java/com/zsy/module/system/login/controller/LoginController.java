package com.zsy.module.system.login.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zsy.common.domain.Result;
import com.zsy.module.system.login.domain.form.SystemUserLoginForm;
import com.zsy.module.system.login.domain.vo.LoginResultVO;
import com.zsy.module.system.login.service.LoginService;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/system/auth")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResultVO> login(@RequestBody SystemUserLoginForm systemUserLoginForm) {
        return loginService.login(systemUserLoginForm);
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    public Result<Void> logout() {
        loginService.logout();
        return Result.success();
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<SystemUserEntity> getUserInfo() {
        SystemUserEntity user = loginService.getUserInfo();
        return Result.success(user);
    }

    /**
     * 检查用户权限
     */
    @GetMapping("/check-permission")
    public Result<Boolean> checkPermission(@RequestParam String permission) {
        boolean hasPermission = StpUtil.hasPermission(permission);
        return Result.success(hasPermission);
    }

    /**
     * 检查用户角色
     */
    @GetMapping("/check-role")
    public Result<Boolean> checkRole(@RequestParam String role) {
        boolean hasRole = StpUtil.hasRole(role);
        return Result.success(hasRole);
    }

    /**
     * 获取用户菜单
     */
    @GetMapping("/menu-list")
    public Result<List<SystemMenuEntity>> getUserMenuList() {
        return Result.success(loginService.getUserMenuList());
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/permission-list")
    public Result<List<SystemMenuEntity>> getUserPermissionList() {
        return Result.success(loginService.getUserPermissionList());
    }
}
