package com.zsy.module.system.chat.config;

import com.zsy.module.system.chat.core.ChatHandshakeInterceptor;
import com.zsy.module.system.chat.core.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * 聊天 WebSocket 配置
 */
@Configuration
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer {

    @Resource
    private ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(new ChatHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}


