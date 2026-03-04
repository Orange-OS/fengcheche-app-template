package com.zsy.module.system.chat.core.session;

import cn.hutool.core.collection.CollUtil;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认的 {@link WebSocketSessionManager} 实现类
 */
public class DefaultWebSocketSessionManager implements WebSocketSessionManager {

    /**
     * id 与 WebSocketSession 映射
     * <p>
     * key：Session 编号
     */
    private final ConcurrentMap<String, WebSocketSession> idSessions = new ConcurrentHashMap<>();

    /**
     * user 与 WebSocketSession 映射
     * <p>
     * key：用户编号
     */
    private final ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    @Override
    public void addSession(WebSocketSession session) {
        // 添加到 idSessions 中
        idSessions.put(session.getId(), session);

        // 添加到 userSessions 中
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId == null) {
            return;
        }

        // 使用 computeIfAbsent 简化代码
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessions.computeIfAbsent(userId,
                k -> new CopyOnWriteArrayList<>());
        sessions.add(session);
    }

    @Override
    public void removeSession(WebSocketSession session) {
        // 移除从 idSessions 中
        idSessions.remove(session.getId());

        // 移除从 idSessions 中
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId == null) {
            return;
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.removeIf(s -> s.getId().equals(session.getId()));

            // 如果该用户的会话列表为空，则移除整个条目
            if (sessions.isEmpty()) {
                userSessions.remove(userId, sessions);
            }
        }
    }

    @Override
    public WebSocketSession getSession(String id) {
        return idSessions.get(id);
    }

    @Override
    public Collection<WebSocketSession> getSessionList(Long userId) {
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessions.get(userId);
        return CollUtil.isNotEmpty(sessions) ? new ArrayList<>(sessions) : new ArrayList<>();
    }

}
