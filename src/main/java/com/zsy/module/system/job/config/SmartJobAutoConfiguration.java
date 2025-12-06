package com.zsy.module.system.job.config;

import com.zsy.module.system.job.core.SmartJob;
import com.zsy.module.system.job.core.SmartJobLauncher;
import com.zsy.module.system.job.service.SmartJobService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 定时任务 配置
 */
@Configuration
@EnableConfigurationProperties(SmartJobConfig.class)
@ConditionalOnProperty(
        prefix = SmartJobConfig.CONFIG_PREFIX,
        name = "enabled",
        havingValue = "true"
)
public class SmartJobAutoConfiguration {

    private final SmartJobConfig jobConfig;

    private final SmartJobService jobService;

    private final List<SmartJob> jobInterfaceList;

    public SmartJobAutoConfiguration(SmartJobConfig jobConfig,
                                     SmartJobService jobService,
                                     List<SmartJob> jobInterfaceList) {
        this.jobConfig = jobConfig;
        this.jobService = jobService;
        this.jobInterfaceList = jobInterfaceList;
    }

    /**
     * 定时任务启动器
     *
     * @return
     */
    @Bean
    public SmartJobLauncher initJobLauncher(RedissonClient redissonClient) {
        return new SmartJobLauncher(jobConfig, jobService, jobInterfaceList, redissonClient);
    }
}
