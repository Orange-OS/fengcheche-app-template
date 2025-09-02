package com.zsy.module.system.heartbeat.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName(value = "system_heart_beat_record")
public class SystemHeartbeatRecordEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long heartBeatRecordId;

    /**
     * 项目路径
     */
    private String projectPath;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 进程号
     */
    private Integer processNo;

    /**
     * 进程开启时间
     */
    private LocalDateTime processStartTime;

    /**
     * 心跳当前时间
     */
    private LocalDateTime heartBeatTime;

}
