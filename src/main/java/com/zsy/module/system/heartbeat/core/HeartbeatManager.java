package com.zsy.module.system.heartbeat.core;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 心跳核心调度管理器
 */
public class HeartbeatManager {

    private static final String THREAD_NAME_PREFIX = "smart-heart-beat";
    private static final int THREAD_COUNT = 1;
    private static final long INITIAL_DELAY = 60 * 1000L;

    private final ScheduledThreadPoolExecutor threadPoolExecutor;

    /**
     * 服务状态持久化处理类
     */
    private final IHeartbeatRecordHandler heartBeatRecordHandler;

    /**
     * 调度配置信息
     */
    private final long intervalMilliseconds;

    /**
     * @param intervalMilliseconds 间隔执行时间(毫秒)
     */
    public HeartbeatManager(Long intervalMilliseconds, IHeartbeatRecordHandler heartBeatRecordHandler) {
        this.intervalMilliseconds = intervalMilliseconds;
        this.heartBeatRecordHandler = heartBeatRecordHandler;
        // 使用守护线程去处理
        this.threadPoolExecutor = new ScheduledThreadPoolExecutor(THREAD_COUNT, r -> {
            Thread t = new Thread(r, THREAD_NAME_PREFIX);
            if (!t.isDaemon()) {
                t.setDaemon(true);
            }
            return t;
        });
        // 开始心跳
        this.beginHeartbeat();
    }

    /**
     * 开启心跳
     */
    private void beginHeartbeat() {
        HeartbeatRunnable heartbeatRunnable = new HeartbeatRunnable(heartBeatRecordHandler);
        threadPoolExecutor.scheduleWithFixedDelay(heartbeatRunnable, INITIAL_DELAY, intervalMilliseconds, TimeUnit.MILLISECONDS);
    }

}
