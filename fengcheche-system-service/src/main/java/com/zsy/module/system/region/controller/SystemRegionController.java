package com.zsy.module.system.region.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zsy.common.domain.Result;
import com.zsy.module.system.region.domain.form.SystemRegionAddUpdateForm;
import com.zsy.module.system.region.domain.vo.SystemRegionTreeNodeVO;
import com.zsy.module.system.region.domain.vo.SystemRegionVO;
import com.zsy.module.system.region.service.SystemRegionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/region")
public class SystemRegionController {
    @Resource
    private SystemRegionService systemRegionService;

    @PostMapping
    @SaCheckPermission("system:region:create")
    public Result<Void> create(@Validated @RequestBody SystemRegionAddUpdateForm addForm) {
        systemRegionService.create(addForm);
        return Result.success();
    }

    @PutMapping("/{code}")
    @SaCheckPermission("system:region:update")
    public Result<Void> update(@PathVariable String code, @Validated @RequestBody SystemRegionAddUpdateForm updateForm) {
        systemRegionService.update(updateForm);
        return Result.success();
    }

    /**
     * 更新状态
     */
    @PutMapping("/{code}/status")
    @SaCheckPermission("system:region:update")
    public Result<Void> updateStatus(
            @PathVariable String code,
            @RequestParam Integer status) {
        systemRegionService.updateStatus(code, status);
        return Result.success();
    }

    @DeleteMapping("/{code}")
    @SaCheckPermission("system:region:delete")
    public Result<Void> delete(@PathVariable String code) {
        systemRegionService.delete(code);
        return Result.success();
    }

    @GetMapping("/{code}")
    @SaCheckPermission("system:region:query")
    public Result<SystemRegionVO> get(@PathVariable String code) {
        return Result.success(systemRegionService.get(code));
    }

    @GetMapping("/tree")
    @SaCheckPermission("system:region:query")
    public Result<List<SystemRegionTreeNodeVO>> tree(
            @RequestParam(required = false, value = "parent", defaultValue = "0") String parent,
            @RequestParam(required = false, value = "status", defaultValue = "-1") Integer status
    ) {
        return Result.success(systemRegionService.tree(parent, status));
    }
}
