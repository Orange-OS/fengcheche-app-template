package com.zsy.module.system.loginlog.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.loginlog.domain.entity.LoginLogEntity;
import com.zsy.module.system.loginlog.mapper.LoginLogMapper;
import org.springframework.stereotype.Service;

/**
 * 登录服务实现类
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogEntity> implements LoginLogService {

} 