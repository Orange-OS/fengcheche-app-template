package com.zsy.module.system.config.domain.form;

import com.zsy.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class SystemConfigPageForm extends PageParam {

    private String configKey;

    private String configName;

}
