package com.zsy.executor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XXL-JOB 执行器配置。
 * 配置项统一放在 xxl.job 下，便于后续迁移到 Nacos 配置中心。
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    /**
     * 本地开发时可关闭执行器注册，避免没有 XXL-JOB Admin 时启动失败。
     */
    private Boolean enabled = true;

    private String accessToken;

    private Admin admin = new Admin();

    private Executor executor = new Executor();

    @Data
    public static class Admin {
        /**
         * XXL-JOB Admin 地址，多个地址用英文逗号分隔。
         */
        private String addresses;
    }

    @Data
    public static class Executor {
        /**
         * 执行器名称，需要和 XXL-JOB Admin 中的 AppName 保持一致。
         */
        private String appname;

        /**
         * 执行器注册地址，通常留空由 XXL-JOB 根据 ip 和 port 自动生成。
         */
        private String address;

        /**
         * 执行器绑定 IP，容器或多网卡环境可显式指定。
         */
        private String ip;

        /**
         * XXL-JOB 调度通信端口，不是 Spring Boot Web 端口。
         */
        private Integer port = 9999;

        private String logPath = "./logs/xxl-job/job-executor";

        private Integer logRetentionDays = 30;
    }
}
