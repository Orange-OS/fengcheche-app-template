package com.zsy.module.system.config.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import com.zsy.module.system.config.domain.form.SystemConfigAddForm;
import com.zsy.module.system.config.domain.form.SystemConfigPageForm;
import com.zsy.module.system.config.domain.form.SystemConfigUpdateForm;

public interface SystemConfigService extends IService<SystemConfigEntity> {

    void create(SystemConfigAddForm systemConfigAddForm);

    void update(SystemConfigUpdateForm systemConfigUpdateForm);

    void delete(Long id);

    Page<SystemConfigEntity> getConfigPage(SystemConfigPageForm systemConfigPageForm);

    SystemConfigEntity getConfigValueByConfigKey(String configKey);
}
