package com.zsy.module.system.feign;

import com.zsy.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * life-service 用药记录内部 Feign 客户端。
 */
@FeignClient(name = "fengcheche-life-service", contextId = "lifeMedicationRecordInternalClient")
public interface LifeMedicationRecordInternalClient {

    /**
     * 通过 Nacos 服务名调用 life-service 内部接口。
     */
    @GetMapping("/fengcheche-life/internal/life/medication-records/count")
    Result<Long> countMedicationRecords();
}
