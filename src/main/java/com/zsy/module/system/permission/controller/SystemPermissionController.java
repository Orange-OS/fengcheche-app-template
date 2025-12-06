package com.zsy.module.system.permission.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zsy.common.domain.Result;
import com.zsy.module.system.permission.domain.form.PermissionTreeUpdateForm;
import com.zsy.module.system.permission.domain.vo.PermissionTreeVO;
import com.zsy.module.system.permission.service.SystemPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统权限控制器
 */
@RestController
@RequestMapping("/system/permission")
public class SystemPermissionController {

    @Resource
    private SystemPermissionService systemPermissionService;

//    /**
//     * 获取权限列表
//     */
//    @GetMapping("/list")
//    @SaCheckPermission("system:permission:query")
//    public Result<List<SystemPermissionEntity>> list() {
//        List<SystemPermissionEntity> permissions = systemPermissionService.list();
//        return Result.success(permissions);
//    }

//    /**
//     * 分页查询权限
//     */
//    @GetMapping
//    @SaCheckPermission("system:permission:query")
//    public Result<Page<SystemPermissionEntity>> page(
//            @RequestParam(defaultValue = "1") Integer pageNum,
//            @RequestParam(defaultValue = "10") Integer pageSize,
//            SystemPermissionEntity permission) {
//        Page<SystemPermissionEntity> page = new Page<>(pageNum, pageSize);
//        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
//
//        // 根据条件构建查询
//        if (permission.getName() != null) {
//            queryWrapper.like(SystemPermissionEntity::getName, permission.getName());
//        }
//        if (permission.getCode() != null) {
//            queryWrapper.like(SystemPermissionEntity::getCode, permission.getCode());
//        }
//        if (permission.getType() != null) {
//            queryWrapper.eq(SystemPermissionEntity::getType, permission.getType());
//        }
//        if (permission.getMenuId() != null) {
//            queryWrapper.eq(SystemPermissionEntity::getMenuId, permission.getMenuId());
//        }
//        if (permission.getStatus() != null) {
//            queryWrapper.eq(SystemPermissionEntity::getStatus, permission.getStatus());
//        }
//
//        // 默认按ID排序
//        queryWrapper.orderByAsc(SystemPermissionEntity::getId);
//
//        systemPermissionService.page(page, queryWrapper);
//        return Result.success(page);
//    }
//
//    /**
//     * 获取权限详情
//     */
//    @GetMapping("/{id}")
//    @SaCheckPermission("system:permission:query")
//    public Result<SystemPermissionEntity> getPermission(@PathVariable Long id) {
//        SystemPermissionEntity permission = systemPermissionService.getById(id);
//        return Result.success(permission);
//    }
//
//    /**
//     * 新增权限
//     */
//    @PostMapping
//    @SaCheckPermission("system:permission:create")
//    public Result<Void> add(@RequestBody SystemPermissionEntity permission) {
//        // 检查权限标识符是否已存在
//        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemPermissionEntity::getCode, permission.getCode());
//        if (systemPermissionService.count(queryWrapper) > 0) {
//            throw BusinessException.of("权限标识符已存在");
//        }
//
//        // 设置默认值
//        if (permission.getStatus() == null) {
//            permission.setStatus(1); // 默认启用
//        }
//
//        systemPermissionService.save(permission);
//        return Result.success();
//    }

    /**
     * 修改权限
     */
    @PutMapping("/{roleId}")
    @SaCheckPermission("system:permission:update")
    public Result<Void> updatePermissionTree(@PathVariable Long roleId, @Valid @RequestBody PermissionTreeUpdateForm updateForm) {
        systemPermissionService.updatePermissionTree(roleId, updateForm.getPermissionIdList());
        return Result.success();
    }

//    /**
//     * 删除权限
//     */
//    @DeleteMapping("/{id}")
//    @SaCheckPermission("system:permission:delete")
//    public Result<Void> delete(@PathVariable Long id) {
//        // 检查权限是否已分配给角色
//        if (systemPermissionService.isPermissionAssigned(id)) {
//            throw BusinessException.of("该权限已分配给角色，无法删除");
//        }
//
//        systemPermissionService.removeById(id);
//        return Result.success();
//    }

//    /**
//     * 根据角色ID获取权限列表
//     */
//    @GetMapping("/role/{roleId}")
//    @SaCheckPermission("system:permission:query")
//    public Result<List<SystemPermissionEntity>> getPermissionsByRoleId(@PathVariable Long roleId) {
//        List<SystemPermissionEntity> permissions = systemPermissionService.getPermissionsByRoleId(roleId);
//        return Result.success(permissions);
//    }
//
//    /**
//     * 根据菜单ID获取权限列表
//     */
//    @GetMapping("/menu/{menuId}")
//    @SaCheckPermission("system:permission:query")
//    public Result<List<SystemPermissionEntity>> getPermissionsByMenuId(@PathVariable Long menuId) {
//        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemPermissionEntity::getMenuId, menuId);
//        List<SystemPermissionEntity> permissions = systemPermissionService.list(queryWrapper);
//        return Result.success(permissions);
//    }

//    /**
//     * 更新权限状态
//     */
//    @PutMapping("/{id}/status")
//    @SaCheckPermission("system:permission:update")
//    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody SystemPermissionEntity statusUpdate) {
//        systemPermissionService.updatePermissionStatus(id, statusUpdate.getStatus());
//        return Result.success();
//    }

    @GetMapping("/tree/{roleId}")
    @SaCheckPermission("system:permission:query")
    public Result<PermissionTreeVO> getPermissionTree(@PathVariable Long roleId) {
        return Result.success(systemPermissionService.getPermissionTree(roleId));
    }

} 