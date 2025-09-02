package com.zsy.module.system.heartbeat.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.heartbeat.domain.entity.SystemHeartbeatRecordEntity;
import com.zsy.module.system.heartbeat.domain.form.SystemHeartbeatPageForm;
import com.zsy.module.system.heartbeat.mapper.SystemHeartbeatMapper;
import com.zsy.module.system.heartbeat.service.SystemHeartbeatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemHeartbeatServiceImpl extends ServiceImpl<SystemHeartbeatMapper, SystemHeartbeatRecordEntity> implements SystemHeartbeatService {

    @Resource
    private SystemHeartbeatMapper systemHeartBeatMapper;

    @Override
    public Page<SystemHeartbeatRecordEntity> getSystemHeartBeatPage(SystemHeartbeatPageForm systemHeartBeatPageForm) {

        Page<SystemHeartbeatRecordEntity> page = new Page<>(systemHeartBeatPageForm.getPageNo(), systemHeartBeatPageForm.getPageSize());

        List<SystemHeartbeatRecordEntity> systemHeartbeatRecordEntityList = systemHeartBeatMapper.page(page, systemHeartBeatPageForm);

        page.setRecords(systemHeartbeatRecordEntityList);
        page.setTotal(page.getTotal());
        page.setPages(page.getPages());

        return page;
    }

}
