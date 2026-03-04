package com.zsy.module.system.chat.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.common.domain.BasePageVO;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.core.handler.JsonWebSocketMessageHandler;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.domain.vo.SystemChatVO;
import com.zsy.module.system.chat.mapper.SystemChatMessageMapper;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 聊天服务实现
 */
@Service
public class SystemChatServiceImpl implements SystemChatService {

    @Resource
    private SystemChatMessageMapper systemChatMessageMapper;

    @Resource
    private SystemUserService systemUserService;

    @Lazy
    @Resource
    private JsonWebSocketMessageHandler jsonWebSocketMessageHandler;

    @Override
    public Result<SystemChatMessageVO> sendMessage(Long fromUserId, SystemChatSendMessageForm form) {
//        createChat()

//        SystemChatMessageVO vo = buildSystemChatMessageVO(entity);
        // 方案1：统一推送消息（无论通过 REST API 还是 WebSocket 发送）
//        jsonWebSocketMessageHandler.pushMessage(vo);

        return Result.success();
    }

    public SystemChatMessageEntity createChat(Long fromUserId, Long toUserId, String content) {
        SystemChatMessageEntity entity = new SystemChatMessageEntity();
        entity.setFromUserId(fromUserId);
        entity.setToUserId(toUserId);
        entity.setContent(content);
        entity.setReadFlag(0);
        entity.setSendTime(LocalDateTime.now());
        // 插入
        systemChatMessageMapper.insert(entity);

        return entity;
    }

    @Override
    public Result<BasePageVO<SystemChatMessageVO>> getChatHistory(Long peerUserId, Integer current, Integer size) {
        Long userId = StpUtil.getLoginIdAsLong();

        Page<SystemChatMessageEntity> page = new Page<>(current, size);
        Page<SystemChatMessageEntity> resultPage =
                (Page<SystemChatMessageEntity>) systemChatMessageMapper.selectChatPage(page, userId, peerUserId);

        List<SystemChatMessageVO> voList = resultPage.getRecords().stream().map(this::buildSystemChatMessageVO).collect(Collectors.toList());

        BasePageVO<SystemChatMessageVO> pageVO = new BasePageVO<>();
        pageVO.setList(voList);
        pageVO.setTotal(resultPage.getTotal());

        // 将当前用户作为接收方且未读的消息标记为已读
        LambdaUpdateWrapper<SystemChatMessageEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemChatMessageEntity::getToUserId, userId)
                .eq(SystemChatMessageEntity::getFromUserId, peerUserId)
                .eq(SystemChatMessageEntity::getReadFlag, 0)
                .set(SystemChatMessageEntity::getReadFlag, 1);
        systemChatMessageMapper.update(null, updateWrapper);

        return Result.success(pageVO);
    }

    @Override
    public Result<Long> getUnreadCount() {
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        LambdaQueryWrapper<SystemChatMessageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemChatMessageEntity::getToUserId, userId)
                .eq(SystemChatMessageEntity::getReadFlag, 0);

        Long count = systemChatMessageMapper.selectCount(wrapper);
        return Result.success(count);
    }

    @Override
    public Result<List<SystemChatVO>> getChatList() {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询聊天列表
        List<SystemChatVO> chatList = systemChatMessageMapper.selectChatList(userId);

        if (chatList == null || chatList.isEmpty()) {
            return Result.success(chatList);
        }

        // 批量查询用户信息，避免N+1查询
        List<Long> peerUserIds = chatList.stream()
                .map(SystemChatVO::getPeerUserId)
                .collect(Collectors.toList());

        List<SystemUserEntity> users = systemUserService.listByIds(peerUserIds);
        Map<Long, SystemUserEntity> userMap = users.stream()
                .collect(Collectors.toMap(SystemUserEntity::getId, user -> user));

        // 填充用户信息
        chatList.forEach(item -> {
            SystemUserEntity peerUser = userMap.get(item.getPeerUserId());
            if (peerUser != null) {
                item.setPeerUsername(peerUser.getUsername());
                item.setPeerNickname(peerUser.getNickname());
                item.setPeerAvatar(peerUser.getAvatar());
            }
        });

        return Result.success(chatList);
    }

    @Override
    public Result<List<SystemChatMessageVO>> getUnreadMessages() {
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        // 查询未读消息
        List<SystemChatMessageEntity> unreadMessages = systemChatMessageMapper.selectUnreadMessages(userId);

        // 转换为 VO
        List<SystemChatMessageVO> voList = unreadMessages.stream().map(this::buildSystemChatMessageVO).collect(Collectors.toList());

        return Result.success(voList);
    }

    private SystemChatMessageVO buildSystemChatMessageVO(SystemChatMessageEntity entity) {
        SystemChatMessageVO vo = new SystemChatMessageVO();
        BeanUtils.copyProperties(entity, vo);

        // 填充接收方用户昵称
        SystemUserEntity fromUser = systemUserService.selectById(entity.getFromUserId());
        if (fromUser != null) {
            vo.setFromNickname(fromUser.getNickname());
            vo.setFromAvatar(fromUser.getAvatar());
        }

        // 填充接收方用户昵称
        SystemUserEntity toUser = systemUserService.selectById(entity.getToUserId());
        if (toUser != null) {
            vo.setToNickname(toUser.getNickname());
            vo.setToAvatar(toUser.getAvatar());
        }

        return vo;
    }

    public void updateReadFlag(Long id, Integer readFlag) {
        systemChatMessageMapper.updateReadFlag(id, readFlag);
    }
}


