//package com.zsy.config;
//
//import com.zsy.module.system.heartbeat.core.HeartBeatManager;
//import com.zsy.module.system.heartbeat.core.IHeartBeatRecordHandler;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
///**
// * 心跳配置
// */
//@Configuration
//public class HeartBeatConfig {
//    /**
//     * 间隔时间
//     */
//    @Value("${heart-beat.interval-seconds}")
//    private Long intervalSeconds;
//
//    @Resource
//    private IHeartBeatRecordHandler heartBeatRecordHandler;
//
//    @Bean
//    public HeartBeatManager heartBeatManager() {
//        return new HeartBeatManager(intervalSeconds * 1000L, heartBeatRecordHandler);
//    }
//}
