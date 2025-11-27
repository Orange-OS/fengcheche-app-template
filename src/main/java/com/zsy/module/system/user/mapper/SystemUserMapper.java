package com.zsy.module.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUserEntity> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SystemUserEntity selectByUsername(@Param("username") String username);

    List<SystemUserEntity> selectListByUsername(@Param("username") String username);

}




