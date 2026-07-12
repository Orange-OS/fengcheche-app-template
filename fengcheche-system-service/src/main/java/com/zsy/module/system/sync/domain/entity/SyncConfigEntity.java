package com.zsy.module.system.sync.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("system_sync_config")
public class SyncConfigEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String syncName;

    private String sourceTable;

    private String targetTable;

    private String primaryKeyColumn;

    private String versionColumn;

    private String deleteFlagColumn;

    private String deleteFlagDeletedValue;

    private String columnList;

    private String cron;

    private Integer batchSize;

    private String lastSyncedVersion;

    private String lastSyncedPk;

    private Boolean enabledFlag;

    private Boolean deletedFlag;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
