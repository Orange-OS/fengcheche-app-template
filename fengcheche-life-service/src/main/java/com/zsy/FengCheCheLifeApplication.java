package com.zsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 生活业务服务启动类，负责用药记录等 life 业务。
 */
@SpringBootApplication
// 开启服务发现，启动后将 life 服务注册到 Nacos。
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zsy")
// 只扫描 life 服务自己的 Mapper，保持业务服务数据访问边界清晰。
@MapperScan("com.zsy.module.life.*.mapper")
public class FengCheCheLifeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheLifeApplication.class, args);
    }
}
