package com.zsy.module.system.healthcheck.controller;

import com.zsy.common.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/health-check")
public class SystemHealthCheckController {

    /**
     * 健康检查
     */
    @GetMapping
    public Result<Boolean> userManagement() {
        try {
            Thread.sleep(50 * 1000); // 50s
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success(true);
    }
}
