package com.zsy.module.system.loginlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.loginlog.domain.entity.LoginLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogEntity> {

}
