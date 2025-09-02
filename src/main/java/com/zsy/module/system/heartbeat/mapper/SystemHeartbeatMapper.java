package com.zsy.module.system.heartbeat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.module.system.heartbeat.domain.entity.SystemHeartbeatRecordEntity;
import com.zsy.module.system.heartbeat.domain.form.SystemHeartbeatPageForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SystemHeartbeatMapper extends BaseMapper<SystemHeartbeatRecordEntity> {

    List<SystemHeartbeatRecordEntity> page(Page<SystemHeartbeatRecordEntity> page, @Param("pageForm") SystemHeartbeatPageForm systemHeartBeatPageForm);

    void updateHeartBeatTimeById(Long heartBeatRecordId, LocalDateTime heartBeatTime);

    SystemHeartbeatRecordEntity query(SystemHeartbeatRecordEntity heartBeatRecordEntity);

}
