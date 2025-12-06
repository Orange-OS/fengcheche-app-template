package com.zsy.module.system.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zsy.module.system.job.domain.entity.SmartJobEntity;
import com.zsy.module.system.job.domain.form.SmartJobQueryForm;
import com.zsy.module.system.job.domain.vo.SmartJobVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务 dao
 */
@Mapper
public interface SmartJobMapper extends BaseMapper<SmartJobEntity> {

    /**
     * 定时任务-分页查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SmartJobVO> query(Page<?> page, @Param("query") SmartJobQueryForm queryForm);

    /**
     * 假删除
     *
     * @param jobId
     * @return
     */
    void updateDeletedFlag(@Param("jobId") Integer jobId, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 根据 任务class 查找
     *
     * @param jobClass
     * @return
     */
    SmartJobEntity selectByJobClass(@Param("jobClass") String jobClass);
}
