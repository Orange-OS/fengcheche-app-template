package com.zsy.module.system.chat.core.listener.impl;

import com.zsy.module.system.chat.core.listener.WebSocketMessageListener;
import com.zsy.module.system.chat.core.listener.message.SystemChatReceiveMessage;
import com.zsy.module.system.chat.service.SystemChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

@Component
public class SystemChatReceiveMessageWebSocketListener implements WebSocketMessageListener<SystemChatReceiveMessage> {

    @Resource
    private SystemChatService systemChatService;

    @Override
    public void onMessage(WebSocketSession session, SystemChatReceiveMessage message) {

        if (message.getMessageId() != null) {
            // 更新已读状态
            systemChatService.updateReadFlag(message.getMessageId(), 1);
        }
    }

    @Override
    public String getType() {
        return "system-chat-receive";
    }

}
