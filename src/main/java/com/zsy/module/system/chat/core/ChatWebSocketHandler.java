package com.zsy.module.system.chat.core;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.mapper.SystemChatMessageMapper;
import com.zsy.module.system.chat.service.SystemChatService;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

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

    @Resource
    private SystemChatMessageMapper chatMessageMapper;

    @Resource
    private SystemUserService systemUserService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId == null) {
            return;
        }
        USER_SESSION_MAP.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
        
        // 方案2：用户上线时，推送所有未读消息
        pushUnreadMessages(userId, session);
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

        // 发送消息（推送逻辑已在 Service 层统一处理）
        Result<SystemChatMessageVO> result = chatService.sendMessage(fromUserId, form);
        
        // 返回结果给发送方（推送已经在 Service 层完成了）
//        sendToSession(session, objectMapper.writeValueAsString(result.getData()));
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

    /**
     * 方案1：统一推送消息（供 Service 层调用）
     * 无论通过 REST API 还是 WebSocket 发送，都通过这个方法推送
     */
    public void pushMessage(SystemChatMessageVO msgVO) {
        if (msgVO == null) {
            return;
        }
        
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(msgVO);
        } catch (Exception e) {
            // ignore
            return;
        }
        
        // 给发送方推送（回显）
        broadcastToUser(msgVO.getFromUserId(), payload);
        // 给接收方推送
        broadcastToUser(msgVO.getToUserId(), payload);
    }

    /**
     * 方案2：用户上线时推送未读消息
     */
    private void pushUnreadMessages(Long userId, WebSocketSession session) {
        try {
            // 查询该用户的所有未读消息
            List<SystemChatMessageEntity> unreadMessages = chatMessageMapper.selectUnreadMessages(userId);
            
            if (unreadMessages == null || unreadMessages.isEmpty()) {
                return;
            }
            
            // 批量查询用户信息，避免N+1查询
            List<Long> fromUserIds = unreadMessages.stream()
                    .map(SystemChatMessageEntity::getFromUserId)
                    .distinct()
                    .collect(Collectors.toList());
            
            List<SystemUserEntity> users = systemUserService.listByIds(fromUserIds);
            Map<Long, SystemUserEntity> userMap = users.stream()
                    .collect(Collectors.toMap(SystemUserEntity::getId, user -> user));
            
            // 逐条推送未读消息
            for (SystemChatMessageEntity entity : unreadMessages) {
                SystemChatMessageVO vo = new SystemChatMessageVO();
                BeanUtils.copyProperties(entity, vo);
                
                // 填充接收方用户昵称
                SystemUserEntity toUser = userMap.get(entity.getToUserId());
                if (toUser != null) {
                    vo.setToNickname(toUser.getNickname());
                    vo.setToAvatar(toUser.getAvatar());
                }
                
                String payload = objectMapper.writeValueAsString(Result.success(vo));
                sendToSession(session, payload);
            }
            
            // 推送完成后，标记为已读
            LambdaUpdateWrapper<SystemChatMessageEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SystemChatMessageEntity::getToUserId, userId)
                    .eq(SystemChatMessageEntity::getReadFlag, 0)
                    .set(SystemChatMessageEntity::getReadFlag, 1);
            chatMessageMapper.update(null, updateWrapper);
            
        } catch (Exception e) {
            // 推送未读消息失败，不影响连接建立
            // ignore
        }
    }
}


