package com.zsy.module.system.config.domain.form;

import lombok.Data;

@Data
public class SystemConfigAddForm {

    private String configKey;

    private String configName;

    private String configValue;

    private String remark;

}
