package com.zsy.module.system.chat.core.listener.message;

import lombok.Data;

@Data
public class SystemChatSendMessage {

    /**
     * 发送给谁
     * 如果为空，说明发送给所有人
     */
    private Long toUserId;
    /**
     * 内容
     */
    private String text;

}
