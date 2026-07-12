package com.zsy.module.life.medicationrecord.controller;

import com.zsy.common.domain.Result;
import com.zsy.module.life.medicationrecord.service.MedicationRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用药记录内部接口，只允许其它服务通过 Feign 调用。
 */
@RestController
@RequestMapping("/internal/life/medication-records")
public class MedicationRecordInternalController {

    @Resource
    private MedicationRecordService medicationRecordService;

    /**
     * 统计用药记录总数。
     */
    @GetMapping("/count")
    public Result<Long> countAll() {
        return Result.success(medicationRecordService.countAll());
    }
}
