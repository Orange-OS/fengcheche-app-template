package com.zsy.module.system.chat.service;

import com.zsy.common.domain.BasePageVO;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.domain.vo.SystemChatVO;

import java.util.List;

/**
 * 聊天服务
 */
public interface SystemChatService {

    /**
     * 发送消息
     *
     * @param fromUserId 发送方用户ID
     * @param form       消息内容
     */
    Result<SystemChatMessageVO> sendMessage(Long fromUserId, SystemChatSendMessageForm form);

    /**
     * 分页获取与某个用户的聊天记录
     */
    Result<BasePageVO<SystemChatMessageVO>> getChatHistory(Long peerUserId, Integer current, Integer size);

    /**
     * 查询当前用户未读消息数量
     */
    Result<Long> getUnreadCount();

    /**
     * 获取当前用户的聊天列表
     */
    Result<List<SystemChatVO>> getChatList();

    /**
     * 查询当前用户的未读消息列表
     */
    Result<List<SystemChatMessageVO>> getUnreadMessages();

    SystemChatMessageEntity createChat(Long fromUserId, Long toUserId, String content);

    /**
     * 更新是否已读状态
     * @param id        消息编号
     * @param readFlag  0-未读 1-已读
     */
    void updateReadFlag(Long id, Integer readFlag);
}


