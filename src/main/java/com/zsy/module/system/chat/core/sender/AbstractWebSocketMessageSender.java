package com.zsy.module.system.chat.core.sender;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zsy.module.system.chat.core.message.JsonWebSocketMessage;
import com.zsy.module.system.chat.core.session.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * WebSocketMessageSender 实现类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {

    private final WebSocketSessionManager webSocketSessionManager;

    @Override
    public void send(Long userId, String messageType, String messageContent) {
        send(null, userId, messageType, messageContent);
    }

    @Override
    public void send(String messageType, String messageContent) {
        send(null, null, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        send(sessionId, null, messageType, messageContent);
    }

    /**
     * 发送消息
     *
     * @param sessionId      Session 编号
     * @param userId         用户编号
     * @param messageType    消息类型
     * @param messageContent 消息内容
     */
    public void send(String sessionId, Long userId, String messageType, String messageContent) {
        // 1. 获得 Session 列表
        List<WebSocketSession> sessions = Collections.emptyList();
        if (StrUtil.isNotEmpty(sessionId)) {
            WebSocketSession session = webSocketSessionManager.getSession(sessionId);
            if (session != null) {
                sessions = Collections.singletonList(session);
            }
        } else if (userId != null) {
            sessions = (List<WebSocketSession>) webSocketSessionManager.getSessionList(userId);
        }
        if (CollUtil.isEmpty(sessions)) {
            if (log.isDebugEnabled()) {
                log.debug("[send][sessionId({}) userId({}) messageType({}) messageContent({}) 未匹配到会话]", sessionId, userId, messageType, messageContent);
            }
        }
        // 2. 执行发送
        doSend(sessions, messageType, messageContent);
    }

    /**
     * 发送消息的具体实现
     *
     * @param sessions       Session 列表
     * @param messageType    消息类型
     * @param messageContent 消息内容
     */
    public void doSend(Collection<WebSocketSession> sessions, String messageType, String messageContent) {
        JsonWebSocketMessage message = new JsonWebSocketMessage().setType(messageType).setContent(messageContent);
        String payload = JSONUtil.toJsonStr(message); // 关键，使用 JSON 序列化
        sessions.forEach(session -> {
            // 1. 各种校验，保证 Session 可以被发送
            if (session == null) {
                log.error("[doSend][session 为空, message({})]", message);
                return;
            }
            if (!session.isOpen()) {
                log.error("[doSend][session({}) 已关闭, message({})]", session.getId(), message);
                return;
            }
            // 2. 执行发送
            try {
                session.sendMessage(new TextMessage(payload));
                log.info("[doSend][session({}) 发送消息成功，message({})]", session.getId(), message);
            } catch (IOException ex) {
                log.error("[doSend][session({}) 发送消息失败，message({})]", session.getId(), message, ex);
            }
        });
    }

}
