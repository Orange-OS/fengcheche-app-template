package com.zsy.module.system.userrole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.userrole.domain.entity.SystemUserRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户-角色关联 Mapper 接口
 */
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRoleEntity> {
    
    /**
     * 根据用户ID查询所有角色ID
     */
    List<Long> selectRoleIdByUserId(@Param("userId") Long userId);

    List<Long> selectIdsByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID删除用户角色关联
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(@Param("userId") Long userId);
} 