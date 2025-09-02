package com.zsy.module.system.heartbeat.core;

import cn.hutool.core.bean.BeanUtil;
import com.zsy.module.system.heartbeat.domain.entity.SystemHeartbeatRecordEntity;
import com.zsy.module.system.heartbeat.mapper.SystemHeartbeatMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 心跳记录
 */
@Slf4j
@Service
public class HeartbeatRecordHandler implements IHeartbeatRecordHandler {

    @Resource
    private SystemHeartbeatMapper systemHeartbeatMapper;

    /**
     * 心跳日志处理方法
     *
     * @param heartbeatRecord
     */
    @Override
    public void handler(HeartbeatRecord heartbeatRecord) {
        SystemHeartbeatRecordEntity heartBeatRecordEntity = BeanUtil.copyProperties(heartbeatRecord, SystemHeartbeatRecordEntity.class);
        SystemHeartbeatRecordEntity heartBeatRecordOld = systemHeartbeatMapper.query(heartBeatRecordEntity);
        if (heartBeatRecordOld == null) {
            systemHeartbeatMapper.insert(heartBeatRecordEntity);
        } else {
            systemHeartbeatMapper.updateHeartBeatTimeById(heartBeatRecordOld.getHeartBeatRecordId(), heartBeatRecordEntity.getHeartBeatTime());
        }
    }

}
