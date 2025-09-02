package com.zsy.module.system.heartbeat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.heartbeat.domain.entity.SystemHeartbeatRecordEntity;
import com.zsy.module.system.heartbeat.domain.form.SystemHeartbeatPageForm;

public interface SystemHeartbeatService extends IService<SystemHeartbeatRecordEntity> {

    Page<SystemHeartbeatRecordEntity> getSystemHeartBeatPage(SystemHeartbeatPageForm systemHeartBeatPageForm);

}
