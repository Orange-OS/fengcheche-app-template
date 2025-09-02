package com.zsy.module.system.heartbeat.domain.form;

import com.zsy.framework.common.pojo.PageParam;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SystemHeartbeatPageForm extends PageParam {

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

}
