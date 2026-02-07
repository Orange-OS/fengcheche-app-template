package com.zsy.module.system.chat.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天列表项
 */
@Data
public class SystemChatListVO implements Serializable {

    /**
     * 对方用户ID
     */
    private Long peerUserId;

    /**
     * 对方用户名
     */
    private String peerUsername;

    /**
     * 对方用户昵称
     */
    private String peerNickname;

    /**
     * 对方用户头像
     */
    private String peerAvatar;

    /**
     * 最后一条消息内容
     */
    private String lastMessage;

    /**
     * 最后一条消息时间
     */
    private LocalDateTime lastMessageTime;

    /**
     * 未读消息数量
     */
    private Long unreadCount;
}

