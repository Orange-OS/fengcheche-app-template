package com.zsy.module.system.sync.core;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import com.zsy.module.system.sync.service.SyncConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SyncConfigScheduler {

    private static final int DEFAULT_POOL_SIZE = 4;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Resource
    private SyncConfigService syncConfigService;

    @Resource
    private SyncExecutor syncExecutor;

    @PostConstruct
    public void init() {
        SyncTaskScheduler.init(DEFAULT_POOL_SIZE);
    }

    @Scheduled(fixedDelayString = "${sync.refresh-interval-ms:60000}")
    @DS("master")
    public void refreshTasks() {
        if (initialized.compareAndSet(false, true)) {
            log.info("SyncConfigScheduler initialized");
        }
        List<SyncConfigEntity> configs = syncConfigService.list(new LambdaQueryWrapper<SyncConfigEntity>()
                .eq(SyncConfigEntity::getDeletedFlag, false));
        List<Long> activeIds = configs.stream().map(SyncConfigEntity::getId).collect(Collectors.toList());
        for (SyncConfigEntity config : configs) {
            if (Boolean.TRUE.equals(config.getEnabledFlag())) {
                if (config.getCron() == null || !CronExpression.isValidExpression(config.getCron())) {
                    continue;
                }
                SyncTaskScheduler.addOrUpdate(config.getId(), () -> syncExecutor.executeById(config.getId()), new CronTrigger(config.getCron()));
            } else if (SyncTaskScheduler.exists(config.getId())) {
                SyncTaskScheduler.remove(config.getId());
            }
        }
        for (Long taskId : SyncTaskScheduler.getTaskIds()) {
            if (!activeIds.contains(taskId)) {
                SyncTaskScheduler.remove(taskId);
            }
        }
    }
}
