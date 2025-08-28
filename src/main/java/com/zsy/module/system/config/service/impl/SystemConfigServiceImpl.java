package com.zsy.module.system.config.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import com.zsy.module.system.config.domain.form.SystemConfigAddForm;
import com.zsy.module.system.config.domain.form.SystemConfigPageForm;
import com.zsy.module.system.config.domain.form.SystemConfigUpdateForm;
import com.zsy.module.system.config.mapper.SystemConfigMapper;
import com.zsy.module.system.config.service.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Override
    public void add(SystemConfigAddForm systemConfigAddForm) {
        systemConfigMapper.insert(BeanUtil.toBean(systemConfigAddForm, SystemConfigEntity.class));
    }

    @Override
    public void update(SystemConfigUpdateForm systemConfigUpdateForm) {
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

}
