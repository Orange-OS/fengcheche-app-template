package com.zsy.module.system.sync.domain.form;

import com.zsy.common.domain.PageParam;
import lombok.Data;

@Data
public class SyncConfigPageForm extends PageParam {

    private String syncName;

    private String sourceTable;

    private Boolean enabledFlag;
}
