package com.zsy.module.system.sync.service.impl;

import com.zsy.module.system.sync.core.SyncExecutor;
import com.zsy.module.system.sync.service.SyncConfigManualService;
import org.springframework.stereotype.Service;

@Service
public class SyncConfigManualServiceImpl implements SyncConfigManualService {

    private final SyncExecutor syncExecutor;

    public SyncConfigManualServiceImpl(SyncExecutor syncExecutor) {
        this.syncExecutor = syncExecutor;
    }

    @Override
    public void executeOnce(Long id, boolean ignoreEnabled) {
        syncExecutor.executeById(id, ignoreEnabled);
    }
}
