package com.zsy.module.system.chat.core.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.json.JSONUtil;
import com.zsy.module.system.chat.core.listener.WebSocketMessageListener;
import com.zsy.module.system.chat.core.message.JsonWebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 聊天 WebSocket 处理器
 */
@Slf4j
@Component
public class JsonWebSocketMessageHandler extends TextWebSocketHandler {

    /**
     * type 与 WebSocketMessageListener 的映射
     */
    private final Map<String, WebSocketMessageListener<Object>> listeners = new HashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public JsonWebSocketMessageHandler(List<? extends WebSocketMessageListener> listenersList) {
        listenersList.forEach((Consumer<WebSocketMessageListener>) listener -> listeners.put(listener.getType(), listener));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1. 获取前端发送的原始消息内容
        String payload = message.getPayload().trim();

        // 2. 空消息，跳过
        if (payload.length() == 0) {
            return;
        }

        // 3. 处理心跳检测：如果是 ping 消息，直接回复 pong 并返回
        if ("ping".equalsIgnoreCase(payload)) {
            // 回复 pong 给前端
            session.sendMessage(new TextMessage("pong"));
            return; // 终止后续逻辑，避免把 ping 当成聊天消息处理
        }

        // 2.1 解析消息
        try {
            // 前端发送的消息体：{ "type": 2, "content": "{}" }
            JsonWebSocketMessage jsonMessage = JSONUtil.toBean(payload, JsonWebSocketMessage.class);
            if (jsonMessage == null) {
                log.error("[handleTextMessage][session({}) message({}) 解析为空]", session.getId(), message.getPayload());
                return;
            }
            if (StrUtil.isEmpty(jsonMessage.getType())) {
                log.error("[handleTextMessage][session({}) message({}) 类型为空]", session.getId(), message.getPayload());
                return;
            }
            // 2.2 获得对应的 WebSocketMessageListener
            WebSocketMessageListener<Object> messageListener = listeners.get(jsonMessage.getType());
            if (messageListener == null) {
                log.error("[handleTextMessage][session({}) message({}) 监听器为空]", session.getId(), message.getPayload());
                return;
            }
            // 2.3 处理消息
            // 获取泛型参数的实际类型
            Class<?> messageType = (Class<?>) TypeUtil.getTypeArgument(messageListener.getClass(), 0);
            // 使用获取到的实际类型进行反序列化
            Object messageObj = JSONUtil.toBean(jsonMessage.getContent(), messageType);

            // 调用消息处理
            messageListener.onMessage(session, messageObj);
        } catch (Throwable e) {
            log.error("[handleTextMessage][session({}) message({}) 处理异常]", session.getId(), message.getPayload());
        }
    }

}


