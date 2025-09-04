package com.zsy.module.system.heartbeat.core;

import cn.hutool.core.net.NetUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 心跳线程
 */
public class HeartbeatRunnable implements Runnable {

    /**
     * 项目路径
     */
    private String projectPath;

    /**
     * 服务器ip（多网卡）
     */
    private List<String> serverIps;

    /**
     * 进程号
     */
    private Integer processNo;

    /**
     * 进程开启时间
     */
    private LocalDateTime processStartTime;

    private IHeartbeatRecordHandler recordHandler;

    public HeartbeatRunnable(IHeartbeatRecordHandler recordHandler) {
        this.recordHandler = recordHandler;
        this.initServerInfo();
    }

    /**
     * 初始化心跳相关信息
     */
    private void initServerInfo() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        this.projectPath = System.getProperty("user.dir");
        this.serverIps = new ArrayList<>(NetUtil.localIpv4s());
        this.processNo = Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
        this.processStartTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(runtimeMXBean.getStartTime()), ZoneId.systemDefault());
    }

    @Override
    public void run() {
        HeartbeatRecord heartbeatRecord = new HeartbeatRecord();
        heartbeatRecord.setProjectPath(this.projectPath);
        heartbeatRecord.setServerIp(StringUtils.join(this.serverIps, ";"));
        heartbeatRecord.setProcessNo(this.processNo);
        heartbeatRecord.setProcessStartTime(this.processStartTime);
        heartbeatRecord.setHeartbeatTime(LocalDateTime.now());
        recordHandler.handler(heartbeatRecord);
    }

}
