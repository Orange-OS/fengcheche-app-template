package com.zsy.module.system.chat.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.common.domain.BasePageVO;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatListVO;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.mapper.SystemChatMessageMapper;
import com.zsy.module.system.chat.service.SystemChatService;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.beans.BeanUtils;
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
    private SystemChatMessageMapper chatMessageMapper;

    @Resource
    private SystemUserService systemUserService;

    @Override
    public Result<SystemChatMessageVO> sendMessage(Long fromUserId, SystemChatSendMessageForm form) {
        SystemChatMessageEntity entity = new SystemChatMessageEntity();
        entity.setFromUserId(fromUserId);
        entity.setToUserId(form.getToUserId());
        entity.setContent(form.getContent());
        entity.setReadFlag(0);
        entity.setSendTime(LocalDateTime.now());

        chatMessageMapper.insert(entity);
        SystemChatMessageVO vo = new SystemChatMessageVO();
        BeanUtils.copyProperties(entity, vo);
        
        // 填充接收方用户昵称
        SystemUserEntity toUser = systemUserService.selectById(form.getToUserId());
        if (toUser != null) {
            vo.setToNickname(toUser.getNickname());
        }
        
        return Result.success(vo);
    }

    @Override
    public Result<BasePageVO<SystemChatMessageVO>> getChatHistory(Long peerUserId, Integer current, Integer size) {
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        Page<SystemChatMessageEntity> page = new Page<>(current, size);
        Page<SystemChatMessageEntity> resultPage =
                (Page<SystemChatMessageEntity>) chatMessageMapper.selectChatPage(page, userId, peerUserId);

        List<SystemChatMessageVO> voList = resultPage.getRecords().stream().map(entity -> {
            SystemChatMessageVO vo = new SystemChatMessageVO();
            BeanUtils.copyProperties(entity, vo);
            
            // 填充接收方用户昵称
            SystemUserEntity toUser = systemUserService.selectById(entity.getToUserId());
            if (toUser != null) {
                vo.setToNickname(toUser.getNickname());
            }
            
            return vo;
        }).collect(Collectors.toList());

        BasePageVO<SystemChatMessageVO> pageVO = new BasePageVO<>();
        pageVO.setList(voList);
        pageVO.setTotal(resultPage.getTotal());

        // 将当前用户作为接收方且未读的消息标记为已读
        LambdaUpdateWrapper<SystemChatMessageEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemChatMessageEntity::getToUserId, userId)
                .eq(SystemChatMessageEntity::getFromUserId, peerUserId)
                .eq(SystemChatMessageEntity::getReadFlag, 0)
                .set(SystemChatMessageEntity::getReadFlag, 1);
        chatMessageMapper.update(null, updateWrapper);

        return Result.success(pageVO);
    }

    @Override
    public Result<Long> getUnreadCount() {
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        LambdaQueryWrapper<SystemChatMessageEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemChatMessageEntity::getToUserId, userId)
                .eq(SystemChatMessageEntity::getReadFlag, 0);

        Long count = chatMessageMapper.selectCount(wrapper);
        return Result.success(count);
    }

    @Override
    public Result<List<SystemChatListVO>> getChatList() {
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        // 查询聊天列表
        List<SystemChatListVO> chatList = chatMessageMapper.selectChatList(userId);

        if (chatList == null || chatList.isEmpty()) {
            return Result.success(chatList);
        }

        // 批量查询用户信息，避免N+1查询
        List<Long> peerUserIds = chatList.stream()
                .map(SystemChatListVO::getPeerUserId)
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
}


