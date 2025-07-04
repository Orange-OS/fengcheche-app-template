package com.zsy.module.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.role.domain.entity.SystemRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper 接口
 */
@Mapper
public interface SystemRoleMapper extends BaseMapper<SystemRoleEntity> {
    
    /**
     * 根据用户ID查询角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SystemRoleEntity> selectByUserId(@Param("userId") Long userId);
}




