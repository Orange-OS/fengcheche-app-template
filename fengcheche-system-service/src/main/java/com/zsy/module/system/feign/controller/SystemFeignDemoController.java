package com.zsy.module.system.feign.controller;

import com.zsy.common.domain.Result;
import com.zsy.module.system.feign.LifeMedicationRecordInternalClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Feign 调用示例接口，用于验证服务间调用链路。
 */
@RestController
@RequestMapping("/system/feign-demo")
public class SystemFeignDemoController {

    @Resource
    private LifeMedicationRecordInternalClient lifeMedicationRecordInternalClient;

    /**
     * system-service 通过 Feign 调用 life-service 内部接口。
     */
    @GetMapping("/life-medication-count")
    public Result<Long> countLifeMedicationRecords() {
        return lifeMedicationRecordInternalClient.countMedicationRecords();
    }
}
