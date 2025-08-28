package com.zsy.module.system.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统菜单 Mapper 接口
 */
@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfigEntity> {

}
