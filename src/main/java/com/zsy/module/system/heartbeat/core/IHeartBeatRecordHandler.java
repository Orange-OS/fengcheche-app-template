package com.zsy.module.system.heartbeat.core;

/**
 * 心跳处理接口
 */
public interface IHeartBeatRecordHandler {
    /**
     * 心跳日志处理方法
     */
    void handler(HeartBeatRecord heartBeatRecord);
}
