package com.zsy.module.system.sync.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import com.zsy.module.system.sync.domain.form.SyncConfigAddForm;
import com.zsy.module.system.sync.domain.form.SyncConfigPageForm;
import com.zsy.module.system.sync.domain.form.SyncConfigUpdateForm;

public interface SyncConfigService extends IService<SyncConfigEntity> {

    void create(SyncConfigAddForm addForm);

    void update(SyncConfigUpdateForm updateForm);

    void delete(Long id);

    void enable(Long id, Boolean enabledFlag);

    Page<SyncConfigEntity> page(SyncConfigPageForm pageForm);

    SyncConfigEntity getByIdOrThrow(Long id);

    void updateCheckpoint(Long id, String lastSyncedVersion, String lastSyncedPk);
}
