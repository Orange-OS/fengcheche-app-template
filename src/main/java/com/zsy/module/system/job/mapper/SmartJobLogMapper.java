package com.zsy.module.system.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zsy.module.system.job.domain.entity.SmartJobLogEntity;
import com.zsy.module.system.job.domain.form.SmartJobLogQueryForm;
import com.zsy.module.system.job.domain.vo.SmartJobLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务-执行记录 dao
 */
@Mapper
public interface SmartJobLogMapper extends BaseMapper<SmartJobLogEntity> {

    /**
     * 定时任务-执行记录-分页查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SmartJobLogVO> query(Page<?> page, @Param("query") SmartJobLogQueryForm queryForm);
}
