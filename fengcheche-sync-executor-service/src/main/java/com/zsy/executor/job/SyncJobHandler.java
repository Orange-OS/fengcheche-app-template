package com.zsy.executor.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 数据同步任务 Handler。
 * 后续 MySQL 到 ClickHouse 的具体同步逻辑可以从这里接入。
 */
@Slf4j
@Component
public class SyncJobHandler {

    @XxlJob("syncDemoJobHandler")
    public void syncDemoJobHandler() {
        String param = XxlJobHelper.getJobParam();
        log.info("XXL-JOB sync demo executed, param={}", param);
        XxlJobHelper.log("同步执行器示例任务执行完成，参数：{}", param);
    }

    @XxlJob("clickHouseSyncJobHandler")
    public void clickHouseSyncJobHandler() {
        String param = XxlJobHelper.getJobParam();
        log.info("XXL-JOB ClickHouse sync task received, param={}", param);
        XxlJobHelper.log("ClickHouse 同步任务已接收，参数：{}", param);
    }
}
