package com.zsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 数据同步 XXL-JOB 执行器服务。
 * 同步任务通常更重、依赖数据源更多，独立部署可以避免影响普通业务任务。
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zsy")
public class FengCheCheSyncExecutorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheSyncExecutorApplication.class, args);
    }
}
