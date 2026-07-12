package com.zsy.executor.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * XXL-JOB 执行器注册配置。
 * XxlJobSpringExecutor 启动后会把当前服务注册到 XXL-JOB Admin。
 */
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobExecutorConfig {

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job", name = "enabled", havingValue = "true", matchIfMissing = true)
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties properties) {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(properties.getAdmin().getAddresses());
        executor.setAppname(properties.getExecutor().getAppname());
        executor.setAddress(properties.getExecutor().getAddress());
        executor.setIp(properties.getExecutor().getIp());
        executor.setPort(properties.getExecutor().getPort());
        executor.setAccessToken(properties.getAccessToken());
        executor.setLogPath(properties.getExecutor().getLogPath());
        executor.setLogRetentionDays(properties.getExecutor().getLogRetentionDays());
        return executor;
    }
}
