package com.zsy.module.system.chat.core.listener.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.zsy.module.system.chat.core.listener.WebSocketMessageListener;
import com.zsy.module.system.chat.core.listener.message.SystemChatReceiveMessage;
import com.zsy.module.system.chat.core.listener.message.SystemChatSendMessage;
import com.zsy.module.system.chat.core.sender.WebSocketMessageSender;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.service.SystemChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

@Component
public class SystemChatSendMessageWebSocketListener implements WebSocketMessageListener<SystemChatSendMessage> {

    @Autowired(required = false) // 由于 fengcheche.websocket.enable 配置项，可以关闭 WebSocket 的功能，所以这里只能不强制注入
    private WebSocketMessageSender webSocketMessageSender;

    @Resource
    private SystemChatService systemChatService;

    @Override
    public void onMessage(WebSocketSession session, SystemChatSendMessage message) {
        // 从会话属性中获取用户ID（在握手拦截器中设置的）
        Long userId = (Long) session.getAttributes().get("userId");

        if (message.getToUserId() != null) {
            SystemChatMessageEntity systemChatMessageEntity = systemChatService.createChat(userId, message.getToUserId(), message.getText());

            SystemChatReceiveMessage toMessage = new SystemChatReceiveMessage()
                    .setMessageId(systemChatMessageEntity.getId())
                    .setFromUserId(userId)
                    .setText(message.getText());
            webSocketMessageSender.sendObject(message.getToUserId(), // 给指定用户
                    "system-chat-receive", toMessage);
            return;
        }
    }

    @Override
    public String getType() {
        return "system-chat-send";
    }

}
