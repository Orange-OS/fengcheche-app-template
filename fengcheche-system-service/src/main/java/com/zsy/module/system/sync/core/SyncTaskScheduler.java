package com.zsy.module.system.sync.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class SyncTaskScheduler {

    private static ThreadPoolTaskScheduler TASK_SCHEDULER;

    private static Map<Long, ScheduledFuture<?>> TASK_MAP;

    private SyncTaskScheduler() {
    }

    public static void init(int poolSize) {
        if (TASK_SCHEDULER != null) {
            return;
        }
        TASK_SCHEDULER = new ThreadPoolTaskScheduler();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("SyncTask-%d").build();
        TASK_SCHEDULER.setThreadFactory(threadFactory);
        TASK_SCHEDULER.setPoolSize(poolSize);
        TASK_SCHEDULER.setWaitForTasksToCompleteOnShutdown(true);
        TASK_SCHEDULER.setAwaitTerminationSeconds(10);
        TASK_SCHEDULER.setErrorHandler((t) -> log.error("SyncTaskScheduler Err:", t));
        TASK_SCHEDULER.setRemoveOnCancelPolicy(true);
        TASK_SCHEDULER.initialize();
        TASK_MAP = new ConcurrentHashMap<>();
    }

    public static void addOrUpdate(Long configId, Runnable task, Trigger trigger) {
        if (TASK_SCHEDULER == null) {
            throw new IllegalStateException("SyncTaskScheduler not initialized");
        }
        remove(configId);
        ScheduledFuture<?> future = TASK_SCHEDULER.schedule(task, trigger);
        TASK_MAP.put(configId, future);
    }

    public static void remove(Long configId) {
        ScheduledFuture<?> future = TASK_MAP.get(configId);
        if (future == null) {
            return;
        }
        future.cancel(false);
        TASK_MAP.remove(configId);
    }

    public static boolean exists(Long configId) {
        return TASK_MAP != null && TASK_MAP.containsKey(configId);
    }

    public static List<Long> getTaskIds() {
        if (TASK_MAP == null || TASK_MAP.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(TASK_MAP.keySet());
    }
}
