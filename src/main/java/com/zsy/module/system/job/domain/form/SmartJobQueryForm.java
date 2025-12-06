package com.zsy.module.system.job.domain.form;

import com.zsy.common.domain.PageParam;
import com.zsy.common.swagger.SchemaEnum;
import com.zsy.common.validator.enumeration.CheckEnum;
import com.zsy.module.system.job.constant.SmartJobTriggerTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 定时任务 分页查询
 */
@Data
public class SmartJobQueryForm extends PageParam {

    @Schema(description = "搜索词|可选")
    @Length(max = 50, message = "搜索词最多50字符")
    private String searchWord;

    @SchemaEnum(desc = "触发类型", value = SmartJobTriggerTypeEnum.class)
    @CheckEnum(value = SmartJobTriggerTypeEnum.class, message = "触发类型错误")
    private String triggerType;

    @Schema(description = "是否启用|可选")
    private Boolean enabledFlag;

    @Schema(description = "是否删除|可选")
    private Boolean deletedFlag;
}
