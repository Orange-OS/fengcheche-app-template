package com.zsy.module.system.sync.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.common.domain.Result;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import com.zsy.module.system.sync.domain.form.SyncConfigAddForm;
import com.zsy.module.system.sync.domain.form.SyncConfigEnableForm;
import com.zsy.module.system.sync.domain.form.SyncConfigExecuteForm;
import com.zsy.module.system.sync.domain.form.SyncConfigPageForm;
import com.zsy.module.system.sync.domain.form.SyncConfigUpdateForm;
import com.zsy.module.system.sync.service.SyncConfigManualService;
import com.zsy.module.system.sync.service.SyncConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sync/config")
public class SyncConfigController {

    @Resource
    private SyncConfigService syncConfigService;

    @Resource
    private SyncConfigManualService syncConfigManualService;

    @PostMapping
    @SaCheckPermission("sync:config:add")
    public Result<Void> create(@Validated @RequestBody SyncConfigAddForm addForm) {
        syncConfigService.create(addForm);
        return Result.success();
    }

    @PutMapping
    @SaCheckPermission("sync:config:update")
    public Result<Void> update(@Validated @RequestBody SyncConfigUpdateForm updateForm) {
        syncConfigService.update(updateForm);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("sync:config:delete")
    public Result<Void> delete(@PathVariable Long id) {
        syncConfigService.delete(id);
        return Result.success();
    }

    @PostMapping("/page")
    @SaCheckPermission("sync:config:query")
    public Result<Page<SyncConfigEntity>> page(@Validated @RequestBody SyncConfigPageForm pageForm) {
        return Result.success(syncConfigService.page(pageForm));
    }

    @GetMapping("/{id}")
    @SaCheckPermission("sync:config:query")
    public Result<SyncConfigEntity> get(@PathVariable Long id) {
        return Result.success(syncConfigService.getByIdOrThrow(id));
    }

    @PutMapping("/{id}/enabled")
    @SaCheckPermission("sync:config:update")
    public Result<Void> enable(@PathVariable Long id, @Validated @RequestBody SyncConfigEnableForm form) {
        syncConfigService.enable(id, form.getEnabledFlag());
        return Result.success();
    }

    @PostMapping("/{id}/execute")
    @SaCheckPermission("sync:config:execute")
    public Result<Void> execute(@PathVariable Long id, @RequestBody(required = false) SyncConfigExecuteForm form) {
        boolean ignoreEnabled = form != null && Boolean.TRUE.equals(form.getIgnoreEnabled());
        syncConfigManualService.executeOnce(id, ignoreEnabled);
        return Result.success();
    }
}
