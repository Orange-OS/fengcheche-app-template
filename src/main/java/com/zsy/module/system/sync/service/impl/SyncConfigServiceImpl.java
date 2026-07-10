package com.zsy.module.system.sync.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.framework.common.exception.DataNotExistException;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import com.zsy.module.system.sync.domain.form.SyncConfigAddForm;
import com.zsy.module.system.sync.domain.form.SyncConfigPageForm;
import com.zsy.module.system.sync.domain.form.SyncConfigUpdateForm;
import com.zsy.module.system.sync.mapper.SyncConfigMapper;
import com.zsy.module.system.sync.service.SyncConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SyncConfigServiceImpl extends ServiceImpl<SyncConfigMapper, SyncConfigEntity> implements SyncConfigService {

    @Override
    public void create(SyncConfigAddForm addForm) {
        SyncConfigEntity entity = new SyncConfigEntity();
        BeanUtils.copyProperties(addForm, entity);
        if (entity.getDeletedFlag() == null) {
            entity.setDeletedFlag(false);
        }
        save(entity);
    }

    @Override
    public void update(SyncConfigUpdateForm updateForm) {
        SyncConfigEntity entity = getByIdOrThrow(updateForm.getId());
        BeanUtils.copyProperties(updateForm, entity);
        updateById(entity);
    }

    @Override
    public void delete(Long id) {
        SyncConfigEntity entity = getByIdOrThrow(id);
        entity.setDeletedFlag(true);
        updateById(entity);
    }

    @Override
    public void enable(Long id, Boolean enabledFlag) {
        SyncConfigEntity entity = getByIdOrThrow(id);
        entity.setEnabledFlag(enabledFlag);
        updateById(entity);
    }

    @Override
    public Page<SyncConfigEntity> page(SyncConfigPageForm pageForm) {
        Page<SyncConfigEntity> page = pageForm.pageParam();
        LambdaQueryWrapper<SyncConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(pageForm.getSyncName()), SyncConfigEntity::getSyncName, pageForm.getSyncName());
        wrapper.like(StrUtil.isNotBlank(pageForm.getSourceTable()), SyncConfigEntity::getSourceTable, pageForm.getSourceTable());
        wrapper.eq(pageForm.getEnabledFlag() != null, SyncConfigEntity::getEnabledFlag, pageForm.getEnabledFlag());
        wrapper.eq(SyncConfigEntity::getDeletedFlag, false);
        return page(page, wrapper);
    }

    @Override
    public SyncConfigEntity getByIdOrThrow(Long id) {
        SyncConfigEntity entity = getById(id);
        if (entity == null || Boolean.TRUE.equals(entity.getDeletedFlag())) {
            throw new DataNotExistException("同步配置不存在");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateCheckpoint(Long id, String lastSyncedVersion, String lastSyncedPk) {
        SyncConfigEntity entity = getByIdOrThrow(id);
        entity.setLastSyncedVersion(lastSyncedVersion);
        entity.setLastSyncedPk(lastSyncedPk);
        updateById(entity);
    }
}
