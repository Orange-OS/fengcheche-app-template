package com.zsy.module.system.chat.core.listener.message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) // 关键注解
public class SystemChatReceiveMessage {
    
    /**
     * 消息编号
     */
    private Long messageId;
    /**
     * 接收人编号
     */
    private Long fromUserId;
    /**
     * 内容
     */
    private String text;

}
