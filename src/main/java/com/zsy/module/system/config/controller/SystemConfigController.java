package com.zsy.module.system.config.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import com.zsy.module.system.config.domain.form.SystemConfigAddForm;
import com.zsy.module.system.config.domain.form.SystemConfigPageForm;
import com.zsy.module.system.config.domain.form.SystemConfigUpdateForm;
import com.zsy.module.system.config.service.SystemConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    @PostMapping
    @SaCheckPermission("system:config:add")
    private Result<Void> add(@RequestBody SystemConfigAddForm systemConfigAddForm) {
        systemConfigService.add(systemConfigAddForm);
        return Result.success();
    }

    @PutMapping
    @SaCheckPermission("system:config:update")
    private Result<Void> update(@RequestBody SystemConfigUpdateForm systemConfigUpdateForm) {
        systemConfigService.update(systemConfigUpdateForm);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("system:config:delete")
    private Result<Void> delete(@PathVariable Long id) {
        systemConfigService.delete(id);
        return Result.success();
    }

    @PostMapping("/page")
    @SaCheckPermission("system:config:query")
    private Result<Page<SystemConfigEntity>> page(@RequestBody SystemConfigPageForm systemConfigPageForm) {
        return Result.success(systemConfigService.getConfigPage(systemConfigPageForm));
    }

}
