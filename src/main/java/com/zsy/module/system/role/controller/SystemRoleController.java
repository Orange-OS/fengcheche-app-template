package com.zsy.module.system.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.role.domain.entity.SystemRoleEntity;
import com.zsy.module.system.role.domain.form.SystemRoleAddForm;
import com.zsy.module.system.role.domain.form.SystemRoleUpdateForm;
import com.zsy.module.system.role.domain.vo.SystemRoleVO;
import com.zsy.module.system.role.service.SystemRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/role")
public class SystemRoleController {
    @Resource
    private SystemRoleService systemRoleService;

    @PostMapping
    @SaCheckPermission("system:role:create")
    public Result<Void> create(@Validated @RequestBody SystemRoleAddForm addForm) {
        systemRoleService.create(addForm);
        return Result.success();
    }

    @PutMapping("/{id}")
    @SaCheckPermission("system:role:update")
    public Result<Void> update(@PathVariable Long id, @Validated @RequestBody SystemRoleUpdateForm updateForm) {
        updateForm.setId(id);
        systemRoleService.update(updateForm);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("system:role:query")
    public Result<Void> delete(@PathVariable Long id) {
        systemRoleService.delete(id);
        return Result.success();
    }

//    @GetMapping("/{id}")
//    public Result<MedicationRecordVO> getDetail(@PathVariable Long id) {
//        return Result.success(systemRoleService.getDetail(id));
//    }

    @GetMapping("/list")
    @SaCheckPermission("system:role:query")
    public Result<List<SystemRoleEntity>> list() {
        return Result.success(systemRoleService.list());
    }

    @GetMapping
    @SaCheckPermission("system:role:query")
    public Result<Page<SystemRoleVO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "code") String code) {
        return Result.success(systemRoleService.page(pageNum, pageSize, name, code));
    }

}
