package com.zsy.module.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.user.domain.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUserEntity> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    SystemUserEntity selectByUsername(String username);

}




