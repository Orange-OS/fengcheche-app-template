package com.zsy.module.system.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.user.domain.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SystemUserController {
    @Resource
    private SystemUserService systemUserService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public Result<List<SystemUserEntity>> list(@RequestParam(required = false, value = "username") String username) {
        return Result.success(systemUserService.getUserList(username));
    }

    /**
     * 分页查询用户
     */
    @GetMapping("/page")
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
    public Result<SystemUserEntity> getUser(@PathVariable Long id) {
        return Result.success(systemUserService.getUserById(id));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<Void> add(@RequestBody SystemUserEntity user) {
        systemUserService.createUser(user);
        return Result.success();
    }

    /**
     * 修改用户
     */
    @PutMapping("/{id}")
    public Result<Void> update(@RequestBody SystemUserEntity user) {
        systemUserService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        systemUserService.deleteUser(id);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    public Result<Void> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        systemUserService.changePassword(userId, oldPassword, newPassword);
        return Result.success();
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password/{id}")
    public Result<Void> resetPassword(@PathVariable Long id) {
        systemUserService.resetPassword(id);
        return Result.success();
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody SystemUserEntity statusUpdate) {
        systemUserService.updateUserStatus(id, statusUpdate.getStatus());
        return Result.success();
    }

}
