package com.zsy.module.system.sync.service;

public interface SyncConfigManualService {

    void executeOnce(Long id, boolean ignoreEnabled);
}
