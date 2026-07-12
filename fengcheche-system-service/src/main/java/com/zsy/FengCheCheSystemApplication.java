package com.zsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 风车车应用程序启动类
 * 集成Sa-Token权限管理框架
 */
@SpringBootApplication
// 开启服务发现，启动后将系统服务注册到 Nacos。
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zsy")
@EnableScheduling
// 只扫描 system 服务自己的 Mapper，避免拆分后误加载其它业务服务的数据访问层。
@MapperScan("com.zsy.module.system.*.mapper")
public class FengCheCheSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheSystemApplication.class, args);
    }
}
