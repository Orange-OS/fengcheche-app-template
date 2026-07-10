package com.zsy.module.system.region.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsy.module.system.region.domain.entity.SystemRegionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper 接口
 */
@Mapper
public interface SystemRegionMapper extends BaseMapper<SystemRegionEntity> {
    
    /**
     * 根据用户ID查询角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SystemRegionEntity> selectByUserId(@Param("userId") Long userId);
}




