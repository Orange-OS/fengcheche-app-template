package com.zsy.module.system.menu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.domain.form.SystemMenuAddForm;
import com.zsy.module.system.menu.service.SystemMenuService;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class SystemMenuController {
    @Resource
    private SystemMenuService systemMenuService;

    @PostMapping
    private Result<Void> create(@RequestBody SystemMenuAddForm addForm) {
        systemMenuService.create(addForm);
        return Result.success();
    }

    @GetMapping("/list")
    private Result<List<SystemMenuEntity>> list() {
        return Result.success(systemMenuService.list());
    }

    @GetMapping
    private Result<Page<SystemMenuEntity>> page(@RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                SystemMenuEntity menu) {
        return Result.success(systemMenuService.page(current, size, menu));
    }

    @GetMapping("/{userId}/menu-tree")
    public Result<List<SystemMenuEntity>> getMenuTreeByUserId(@PathVariable Long userId) {

        List<SystemMenuEntity> menuTree = systemMenuService.getMenuTreeByUserId(userId);

//        if (menuTree == null || menuTree.isEmpty()) {
//            return Result.notFound().build();
//        }

        return Result.success(menuTree);
    }

}
