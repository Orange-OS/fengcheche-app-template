package com.zsy.module.system.chat.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.service.SystemChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 聊天 WebSocket 处理器
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    /**
     * 用户ID -> 该用户所有 WebSocketSession
     */
    private static final Map<Long, Set<WebSocketSession>> USER_SESSION_MAP = new ConcurrentHashMap<>();

    @Resource
    private SystemChatService chatService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId == null) {
            return;
        }
        USER_SESSION_MAP.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long fromUserId = (Long) session.getAttributes().get("userId");
        if (fromUserId == null) {
            return;
        }

        // 前端发送的消息体：{ "toUserId": 2, "content": "你好" }
        SystemChatSendMessageForm form =
                objectMapper.readValue(message.getPayload(), SystemChatSendMessageForm.class);

        Result<SystemChatMessageVO> result = chatService.sendMessage(fromUserId, form);
        if (!result.isSuccess()) {
            // 发送失败时，给发送方返回错误信息
            sendToSession(session, objectMapper.writeValueAsString(result));
            return;
        }

        SystemChatMessageVO msgVO = result.getData();
        String payload = objectMapper.writeValueAsString(Result.success(msgVO));

        // 给发送方推送
        broadcastToUser(fromUserId, payload);
        // 给接收方推送
        broadcastToUser(msgVO.getToUserId(), payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId == null) {
            return;
        }
        Set<WebSocketSession> sessions = USER_SESSION_MAP.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                USER_SESSION_MAP.remove(userId);
            }
        }
    }

    private void broadcastToUser(Long userId, String payload) {
        if (userId == null) {
            return;
        }
        Set<WebSocketSession> sessions = USER_SESSION_MAP.get(userId);
        if (sessions == null) {
            return;
        }
        for (WebSocketSession ws : sessions) {
            if (ws.isOpen()) {
                sendToSession(ws, payload);
            }
        }
    }

    private void sendToSession(WebSocketSession session, String payload) {
        try {
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            // ignore send error
        }
    }
}


