package com.zsy.framework.config;

import com.zsy.module.system.heartbeat.core.HeartbeatManager;
import com.zsy.module.system.heartbeat.core.IHeartbeatRecordHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 心跳配置
 */
@Configuration
public class HeartBeatConfig {

    /**
     * 间隔时间
     */
    @Value("${heart-beat.interval-seconds}")
    private Long intervalSeconds;

    @Resource
    private IHeartbeatRecordHandler heartBeatRecordHandler;

    @Bean
    public HeartbeatManager heartBeatManager() {
        return new HeartbeatManager(intervalSeconds * 1000L, heartBeatRecordHandler);
    }

}
