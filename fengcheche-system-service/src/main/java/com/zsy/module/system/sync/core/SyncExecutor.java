package com.zsy.module.system.sync.core;

import cn.hutool.core.util.StrUtil;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import com.zsy.module.system.sync.repository.ClickHouseSinkRepository;
import com.zsy.module.system.sync.repository.MySqlSyncRepository;
import com.zsy.module.system.sync.service.SyncConfigService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SyncExecutor {

    private static final String EXECUTE_LOCK = "sync-config-lock-";

    private final MySqlSyncRepository mySqlSyncRepository;
    private final ClickHouseSinkRepository clickHouseSinkRepository;
    private final SyncConfigService syncConfigService;

    @Autowired(required = false)
    private RedissonClient redissonClient;

    public SyncExecutor(MySqlSyncRepository mySqlSyncRepository,
                        ClickHouseSinkRepository clickHouseSinkRepository,
                        SyncConfigService syncConfigService) {
        this.mySqlSyncRepository = mySqlSyncRepository;
        this.clickHouseSinkRepository = clickHouseSinkRepository;
        this.syncConfigService = syncConfigService;
    }

    public void executeById(Long id, boolean ignoreEnabled) {
        SyncConfigEntity config = syncConfigService.getByIdOrThrow(id);
        execute(config, ignoreEnabled);
    }

    public void executeById(Long id) {
        executeById(id, false);
    }

    public void execute(SyncConfigEntity config) {
        execute(config, false);
    }

    public void execute(SyncConfigEntity config, boolean ignoreEnabled) {
        if (config == null || Boolean.TRUE.equals(config.getDeletedFlag())
                || (!ignoreEnabled && !Boolean.TRUE.equals(config.getEnabledFlag()))) {
            return;
        }
        RLock lock = null;
        boolean locked = false;
        try {
            SyncSqlValidator.validateTableIdentifier(config.getSourceTable(), "源表");
            SyncSqlValidator.validateTableIdentifier(config.getTargetTable(), "目标表");
            SyncSqlValidator.validateIdentifier(config.getPrimaryKeyColumn(), "主键列");
            SyncSqlValidator.validateIdentifier(config.getVersionColumn(), "版本列");
            SyncSqlValidator.validateIdentifier(config.getDeleteFlagColumn(), "软删列");

            if (redissonClient != null) {
                lock = redissonClient.getLock(EXECUTE_LOCK + config.getId());
                locked = lock.tryLock(0, 30, TimeUnit.SECONDS);
                if (!locked) {
                    return;
                }
            }
            doSync(config);
        } catch (Throwable t) {
            log.error("SyncExecutor error, configId={}", config.getId(), t);
        } finally {
            if (lock != null && locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private void doSync(SyncConfigEntity config) {
        List<String> columns = SyncSqlValidator.parseColumnList(config.getColumnList());
        if (!columns.isEmpty()) {
            SyncSqlValidator.ensureContains(columns, config.getPrimaryKeyColumn());
            SyncSqlValidator.ensureContains(columns, config.getVersionColumn());
            SyncSqlValidator.ensureContains(columns, config.getDeleteFlagColumn());
        }
        String selectColumns = columns.isEmpty() ? "*" : String.join(",", columns);
        int batchSize = config.getBatchSize() != null && config.getBatchSize() > 0 ? config.getBatchSize() : 1000;

        String lastVersion = config.getLastSyncedVersion();
        String lastPk = config.getLastSyncedPk();

        List<String> effectiveColumns = columns.isEmpty() ? null : new ArrayList<>(columns);
        while (true) {
            List<Map<String, Object>> rows = mySqlSyncRepository.queryBatch(config, lastVersion, lastPk, batchSize, selectColumns);
            if (rows.isEmpty()) {
                return;
            }
            if (effectiveColumns == null) {
                effectiveColumns = new ArrayList<>(rows.get(0).keySet());
                SyncSqlValidator.ensureContains(effectiveColumns, config.getPrimaryKeyColumn());
                SyncSqlValidator.ensureContains(effectiveColumns, config.getVersionColumn());
                SyncSqlValidator.ensureContains(effectiveColumns, config.getDeleteFlagColumn());
            }
            clickHouseSinkRepository.batchInsert(config.getTargetTable(), effectiveColumns, rows);

            Map<String, Object> lastRow = rows.get(rows.size() - 1);
            Object versionVal = lastRow.get(config.getVersionColumn());
            Object pkVal = lastRow.get(config.getPrimaryKeyColumn());
            String newVersion = versionVal == null ? lastVersion : String.valueOf(versionVal);
            String newPk = pkVal == null ? lastPk : String.valueOf(pkVal);
            if (StrUtil.isNotBlank(newVersion)) {
                syncConfigService.updateCheckpoint(config.getId(), newVersion, newPk);
                lastVersion = newVersion;
                lastPk = newPk;
            }

            if (rows.size() < batchSize) {
                return;
            }
        }
    }
}
