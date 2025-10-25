package com.zsy.module.system.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.domain.form.SystemMenuAddForm;
import com.zsy.module.system.menu.domain.form.SystemMenuUpdateForm;
import com.zsy.module.system.menu.service.SystemMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class SystemMenuController {
    @Resource
    private SystemMenuService systemMenuService;

    /**
     * 新增菜单
     */
    @PostMapping
    @SaCheckPermission("system:menu:create")
    private Result<Void> create(@RequestBody SystemMenuAddForm addForm) {
        systemMenuService.create(addForm);
        return Result.success();
    }

    /**
     * 修改菜单
     */
    @PutMapping
    @SaCheckPermission("system:menu:update")
    public Result<Void> update(@RequestBody SystemMenuUpdateForm updateForm) {
        systemMenuService.update(updateForm);
        return Result.success();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:menu:delete")
    public Result<Void> delete(@PathVariable Long id) {
        systemMenuService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    @SaCheckPermission("system:menu:query")
    private Result<List<SystemMenuEntity>> list() {
        return Result.success(systemMenuService.list());
    }

    @GetMapping
    @SaCheckPermission("system:menu:query")
    private Result<Page<SystemMenuEntity>> page(@RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                SystemMenuEntity menu) {
        return Result.success(systemMenuService.page(current, size, menu));
    }

    @GetMapping("/{userId}/menu-tree")
    @SaCheckPermission("system:menu:query")
    public Result<List<SystemMenuEntity>> getMenuTreeByUserId(@PathVariable Long userId) {

        if (StpUtil.hasRole("admin")) {
            userId = null;
        }

        List<SystemMenuEntity> menuTree = systemMenuService.getMenuTreeByUserId(userId);

        return Result.success(menuTree);
    }

    /**
     * 更新菜单状态
     */
    @PutMapping("/{id}/status")
    @SaCheckPermission("system:menu:update")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody SystemMenuEntity statusUpdate) {
        systemMenuService.updateMenuStatus(id, statusUpdate.getStatus());
        return Result.success();
    }

}
