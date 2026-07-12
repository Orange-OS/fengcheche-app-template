package com.zsy.executor.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通用业务任务 Handler。
 * 在 XXL-JOB Admin 中配置 JobHandler 时填写这里的 @XxlJob 名称。
 */
@Slf4j
@Component
public class BusinessJobHandler {

    @XxlJob("businessDemoJobHandler")
    public void businessDemoJobHandler() {
        String param = XxlJobHelper.getJobParam();
        log.info("XXL-JOB business demo executed, param={}", param);
        XxlJobHelper.log("业务执行器示例任务执行完成，参数：{}", param);
    }

    @XxlJob("businessHealthCheckJobHandler")
    public void businessHealthCheckJobHandler() {
        log.info("XXL-JOB business executor health check executed");
        XxlJobHelper.log("业务执行器健康检查任务执行完成");
    }
}
