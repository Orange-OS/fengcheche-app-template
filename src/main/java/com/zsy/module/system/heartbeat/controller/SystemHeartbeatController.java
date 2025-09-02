package com.zsy.module.system.heartbeat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.heartbeat.domain.entity.SystemHeartbeatRecordEntity;
import com.zsy.module.system.heartbeat.domain.form.SystemHeartbeatPageForm;
import com.zsy.module.system.heartbeat.service.SystemHeartbeatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class SystemHeartbeatController {

    @Resource
    private SystemHeartbeatService systemHeartBeatService;

    @PostMapping("/system/heartBeat/page")
    public Result<Page<SystemHeartbeatRecordEntity>> page(@RequestBody @Valid SystemHeartbeatPageForm systemHeartBeatPageForm) {
        return Result.success(systemHeartBeatService.getSystemHeartBeatPage(systemHeartBeatPageForm));
    }

}
