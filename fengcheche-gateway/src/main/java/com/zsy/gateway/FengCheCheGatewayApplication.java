package com.zsy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类，统一承接外部请求并转发到后端微服务。
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FengCheCheGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FengCheCheGatewayApplication.class, args);
    }
}
