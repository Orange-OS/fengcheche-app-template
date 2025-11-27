package com.zsy.module.life.medicationrecord.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordAddForm;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordUpdateForm;
import com.zsy.module.life.medicationrecord.domain.vo.MedicationRecordVO;
import com.zsy.module.life.medicationrecord.service.MedicationRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/life/medication-records")
public class MedicationRecordController {

    @Resource
    private MedicationRecordService medicationRecordService;

    @PostMapping
    @SaCheckPermission("life:medication:record:create")
    public Result<Void> create(@Validated @RequestBody MedicationRecordAddForm addForm) {
        medicationRecordService.create(addForm);
        return Result.success();
    }

    @PutMapping("/{id}")
    @SaCheckPermission("life:medication:record:update")
    public Result<Void> update(@PathVariable Long id, @Validated @RequestBody MedicationRecordUpdateForm updateForm) {
        updateForm.setId(id);
        medicationRecordService.update(updateForm);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("life:medication:record:delete")
    public Result<Void> delete(@PathVariable Long id) {
        medicationRecordService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @SaCheckPermission("life:medication:record:query")
    public Result<MedicationRecordVO> getDetail(@PathVariable Long id) {
        return Result.success(medicationRecordService.getDetail(id));
    }

    @GetMapping("/list")
    @SaCheckPermission("life:medication:record:query")
    public Result<List<MedicationRecordVO>> list(
            @RequestParam(required = false, value = "medicineName") String medicineName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.success(medicationRecordService.list(medicineName, startDate, endDate));
    }

    @GetMapping
    @SaCheckPermission("life:medication:record:query")
    public Result<Page<MedicationRecordVO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, value = "medicineName") String medicineName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.success(medicationRecordService.page(pageNum, pageSize, medicineName, startDate, endDate));
    }
}