package com.zsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 风车车应用程序启动类
 * 集成Sa-Token权限管理框架
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.zsy.module.*.*.mapper")
public class FengCheCheApplication {
    public static void main(String[] args) {
        SpringApplication.run(FengCheCheApplication.class, args);
    }
}
