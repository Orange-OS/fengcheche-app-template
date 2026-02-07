package com.zsy.module.system.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户聊天消息表
 */
@TableName("system_chat_message")
@Data
public class SystemChatMessageEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送方用户ID
     */
    private Long fromUserId;

    /**
     * 接收方用户ID
     */
    private Long toUserId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读（0-未读 1-已读）
     */
    private Integer readFlag;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}


