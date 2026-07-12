package com.zsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 通用业务 XXL-JOB 执行器服务。
 * 适合承载轻量业务任务、通知任务、数据修复任务等非同步专用作业。
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zsy")
public class FengCheCheJobExecutorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheJobExecutorApplication.class, args);
    }
}
