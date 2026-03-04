package com.zsy.module.system.chat.core.config;

import com.zsy.module.system.chat.core.ChatHandshakeInterceptor;
import com.zsy.module.system.chat.core.handler.JsonWebSocketMessageHandler;
import com.zsy.module.system.chat.core.listener.WebSocketMessageListener;
import com.zsy.module.system.chat.core.sender.local.LocalWebSocketMessageSender;
import com.zsy.module.system.chat.core.session.DefaultWebSocketSessionManager;
import com.zsy.module.system.chat.core.session.WebSocketSessionHandlerDecorator;
import com.zsy.module.system.chat.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 聊天 WebSocket 配置
 */
@Slf4j
@Configuration
@EnableWebSocket
@ConditionalOnProperty(prefix = "fengcheche.websocket", value = "enable", matchIfMissing = true)
// 允许使用 fengcheche.websocket.enable=false 禁用 websocket
public class WebSocketConfig {

    @PostConstruct
    public void init() {
        log.info("========== WebSocket 服务已启动 ==========");
        log.info("WebSocket 连接地址: /ws/chat");
        log.info("========================================");
    }

    @Bean
    public WebSocketConfigurer registerWebSocketHandlers(WebSocketHandler webSocketHandler) {
        return registry -> registry
                .addHandler(webSocketHandler, "/ws/chat")
                .addInterceptors(new ChatHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new DefaultWebSocketSessionManager();
    }

    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. 创建 JsonWebSocketMessageHandler 对象，处理消息
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. 创建 WebSocketSessionHandlerDecorator 对象，处理连接
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    // ==================== Sender 相关 ====================

    @Configuration
    @ConditionalOnProperty(prefix = "fengcheche.websocket", name = "sender-type", havingValue = "local")
    public static class LocalWebSocketMessageSenderConfiguration {

        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }

}


