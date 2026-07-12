package com.zsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 报表服务启动类，负责图表生成等工具型能力。
 */
@SpringBootApplication
// 开启服务发现，启动后将 report 服务注册到 Nacos。
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zsy")
public class FengCheCheReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheReportApplication.class, args);
    }
}
