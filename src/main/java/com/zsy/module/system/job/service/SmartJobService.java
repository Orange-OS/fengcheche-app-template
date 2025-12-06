package com.zsy.module.system.job.service;

import com.zsy.module.system.job.domain.entity.SmartJobEntity;
import com.zsy.module.system.job.domain.entity.SmartJobLogEntity;
import com.zsy.module.system.job.mapper.SmartJobMapper;
import com.zsy.module.system.job.mapper.SmartJobLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * job 持久化业务
 *
 * @author huke
 * @date 2024/6/22 22:28
 */
@Service
public class SmartJobService {

    @Autowired
    private SmartJobMapper jobDao;

    @Autowired
    private SmartJobLogMapper jobLogDao;

    public SmartJobMapper getJobDao() {
        return jobDao;
    }

    public SmartJobLogMapper getJobLogDao() {
        return jobLogDao;
    }

    /**
     * 保存执行记录
     *
     * @param logEntity
     * @param jobEntity
     */
    @Transactional(rollbackFor = Throwable.class)
    public void saveLog(SmartJobLogEntity logEntity, SmartJobEntity jobEntity) {
        jobLogDao.insert(logEntity);

        jobEntity.setLastExecuteLogId(logEntity.getLogId());
        jobDao.updateById(jobEntity);
    }
}
