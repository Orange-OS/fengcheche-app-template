package com.zsy.module.system.sync.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SyncConfigAddForm {

    @NotBlank(message = "同步名称不能为空")
    private String syncName;

    @NotBlank(message = "源表不能为空")
    private String sourceTable;

    @NotBlank(message = "目标表不能为空")
    private String targetTable;

    @NotBlank(message = "主键列不能为空")
    private String primaryKeyColumn;

    @NotBlank(message = "版本列不能为空")
    private String versionColumn;

    @NotBlank(message = "软删列不能为空")
    private String deleteFlagColumn;

    @NotBlank(message = "软删删除值不能为空")
    private String deleteFlagDeletedValue;

    private String columnList;

    @NotBlank(message = "cron 不能为空")
    private String cron;

    @NotNull(message = "批量大小不能为空")
    private Integer batchSize;

    private String lastSyncedVersion;

    private String lastSyncedPk;

    @NotNull(message = "启用标识不能为空")
    private Boolean enabledFlag;

    private String remark;
}
