package com.zsy.module.system.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.sync.domain.entity.SyncConfigEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SyncConfigMapper extends BaseMapper<SyncConfigEntity> {
}
