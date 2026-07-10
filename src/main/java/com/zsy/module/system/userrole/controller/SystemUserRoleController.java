package com.zsy.module.system.userrole.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsy.common.domain.Result;
import com.zsy.module.system.userrole.domain.entity.SystemUserRoleEntity;
import com.zsy.module.system.userrole.domain.vo.SystemUserRoleVO;
import com.zsy.module.system.userrole.service.SystemUserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/system/user-role")
public class SystemUserRoleController {
    @Resource
    private SystemUserRoleService systemUserRoleService;

    /**
     * 创建或更新用户角色
     */
    @PutMapping("/create-update/{userId}")
    @SaCheckPermission("system:user:role:create")
    public Result<Void> createUpdateUserRole(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIdList) {
        systemUserRoleService.createUpdateUserRole(userId, roleIdList);
        return Result.success();
    }

    /**
     * 获取用户角色列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:user:role:query")
    public Result<List<SystemUserRoleVO>> getUserRoleList(@RequestParam(required = false, value = "username") String username) {
        return Result.success(systemUserRoleService.getUserRoleList(username));
    }

    /**
     * 获取用户角色列表
     */
    @GetMapping("/list/{userId}")
    @SaCheckPermission("system:user:role:query")
    public Result<List<SystemUserRoleEntity>> list(@PathVariable("userId") Long userId) {
        return Result.success(
                systemUserRoleService.list(new LambdaQueryWrapper<SystemUserRoleEntity>()
                        .eq(SystemUserRoleEntity::getUserId, userId)
                )
        );
    }
}
