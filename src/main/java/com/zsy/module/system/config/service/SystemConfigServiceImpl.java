package com.zsy.module.system.config.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.framework.common.exception.DataExistException;
import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import com.zsy.module.system.config.domain.form.SystemConfigAddForm;
import com.zsy.module.system.config.domain.form.SystemConfigPageForm;
import com.zsy.module.system.config.domain.form.SystemConfigUpdateForm;
import com.zsy.module.system.config.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Override
    public void create(SystemConfigAddForm systemConfigAddForm) {
        // 校验 configKey 是否存在
        checkConfigKeyExist(systemConfigAddForm.getConfigKey());
        // 插入
        systemConfigMapper.insert(BeanUtil.toBean(systemConfigAddForm, SystemConfigEntity.class));
    }

    private SystemConfigEntity getByConfigKey(String configKey) {
        return systemConfigMapper.selectByConfigKey(configKey);
    }

    public void checkConfigKeyExist(String configKey) {
        SystemConfigEntity systemConfigEntity = getByConfigKey(configKey);
        if (systemConfigEntity != null) {
            throw new DataExistException("参数键：{}已经存在", configKey);
        }
    }

    public void checkConfigKeyNotExist(String configKey) {
        SystemConfigEntity systemConfigEntity = getByConfigKey(configKey);
        if (systemConfigEntity != null) {
            throw new DataExistException("参数键：{}不存在", configKey);
        }
    }

    @Override
    public void update(SystemConfigUpdateForm systemConfigUpdateForm) {
        // 校验 configKey 是否存在
        checkConfigKeyExist(systemConfigUpdateForm.getConfigKey());
        // 更新
        systemConfigMapper.updateById(BeanUtil.copyProperties(systemConfigUpdateForm, SystemConfigEntity.class));
    }

    @Override
    public void delete(Long id) {
        systemConfigMapper.deleteById(id);
    }

    @Override
    public Page<SystemConfigEntity> getConfigPage(SystemConfigPageForm systemConfigPageForm) {

        Page<SystemConfigEntity> page = new Page<>(systemConfigPageForm.getPageNo(), systemConfigPageForm.getPageSize());
        LambdaQueryWrapper<SystemConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(systemConfigPageForm.getConfigKey()), SystemConfigEntity::getConfigKey, systemConfigPageForm.getConfigKey());
        queryWrapper.like(StrUtil.isNotBlank(systemConfigPageForm.getConfigName()), SystemConfigEntity::getConfigName, systemConfigPageForm.getConfigName());

        return this.page(page, queryWrapper);
    }

    @Override
    public SystemConfigEntity getConfigValueByConfigKey(String configKey) {
        checkConfigKeyNotExist(configKey);
        return getByConfigKey(configKey);
    }
}
