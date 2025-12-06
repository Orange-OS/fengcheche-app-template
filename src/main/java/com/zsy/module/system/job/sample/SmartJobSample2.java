package com.zsy.module.system.job.sample;

import com.zsy.module.system.config.domain.entity.SystemConfigEntity;
import com.zsy.module.system.config.mapper.SystemConfigMapper;
import com.zsy.module.system.job.core.SmartJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 定时任务 示例2
 */
@Slf4j
@Service
public class SmartJobSample2 implements SmartJob {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    /**
     * 定时任务示例
     * 需要事务时 添加 @Transactional 注解
     *
     * @param param 可选参数 任务不需要时不用管
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public String run(String param) {
        // 随便更新点什么东西
        SystemConfigEntity systemConfigEntity = new SystemConfigEntity();
        systemConfigEntity.setId(1L);
        systemConfigEntity.setRemark(param);
        systemConfigMapper.updateById(systemConfigEntity);

        systemConfigEntity = new SystemConfigEntity();
        systemConfigEntity.setId(2L);
        systemConfigEntity.setRemark("SmartJob Sample2 update");
        systemConfigMapper.updateById(systemConfigEntity);

        return "执行成功,本次处理数据1条";
    }
}
