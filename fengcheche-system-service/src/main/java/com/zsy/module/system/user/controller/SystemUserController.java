package com.zsy.module.system.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.common.domain.Result;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.domain.form.SystemUserCreateForm;
import com.zsy.module.system.user.domain.form.SystemUserUpdateForm;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/system/user")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;


    /**
     * 新增用户
     */
    @PostMapping
    @SaCheckPermission("system:user:create")
    public Result<Void> create(@RequestBody SystemUserCreateForm createForm) {
        return systemUserService.createUser(createForm);
    }

    /**
     * 修改用户
     */
    @PutMapping("/{id}")
    @SaCheckPermission("system:user:update")
    public Result<Void> update(@RequestBody SystemUserUpdateForm updateForm) {
        systemUserService.updateUser(updateForm);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:user:delete")
    public Result<Void> delete(@PathVariable Long id) {
        systemUserService.deleteUser(id);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    @SaCheckPermission("system:user:update")
    public Result<Void> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return systemUserService.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password/{id}")
    @SaCheckPermission("system:user:update")
    public Result<Void> resetPassword(@PathVariable Long id) {
        return systemUserService.resetPassword(id);
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:user:update")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody SystemUserEntity statusUpdate) {
        return systemUserService.updateUserStatus(id, statusUpdate.getStatus());
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:user:query")
    public Result<List<SystemUserEntity>> list(@RequestParam(required = false, value = "username") String username) {
        return Result.success(systemUserService.getUserList(username));
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
    @SaCheckPermission("system:user:query")
    public Result<Page<SystemUserEntity>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            SystemUserEntity user) {
        return Result.success(systemUserService.getUserPage(current, size, user));
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    @SaCheckPermission("system:user:query")
    public Result<SystemUserEntity> getUser(@PathVariable Long id) {
        return Result.success(systemUserService.getUserById(id));
    }
}
