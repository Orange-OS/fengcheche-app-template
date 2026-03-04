package com.zsy.module.system.chat.core.session;

import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

/**
 * {@link WebSocketSession} 管理器的接口
 */
public interface WebSocketSessionManager {

    /**
     * 添加 Session
     *
     * @param session Session
     */
    void addSession(WebSocketSession session);

    /**
     * 移除 Session
     *
     * @param session Session
     */
    void removeSession(WebSocketSession session);

    /**
     * 获得指定编号的 Session
     *
     * @param id Session 编号
     * @return Session
     */
    WebSocketSession getSession(String id);

    /**
     * 获得指定用户编号的 Session 列表
     *
     * @param userId 用户编号
     * @return Session 列表
     */
    Collection<WebSocketSession> getSessionList(Long userId);

}