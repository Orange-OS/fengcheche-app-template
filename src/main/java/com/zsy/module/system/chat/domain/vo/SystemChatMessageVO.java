package com.zsy.module.system.chat.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息返回对象
 */
@Data
public class SystemChatMessageVO implements Serializable {

    private Long id;

    private Long fromUserId;

    private Long toUserId;

    /**
     * 接收方用户昵称
     */
    private String toNickname;

    private String content;

    private Integer readFlag;

    private LocalDateTime sendTime;
}


