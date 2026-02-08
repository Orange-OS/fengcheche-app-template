package com.zsy.module.system.chat.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zsy.common.domain.BasePageVO;
import com.zsy.common.domain.Result;
import com.zsy.module.system.chat.domain.form.SystemChatSendMessageForm;
import com.zsy.module.system.chat.domain.vo.SystemChatMessageVO;
import com.zsy.module.system.chat.domain.vo.SystemChatVO;
import com.zsy.module.system.chat.service.SystemChatService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户聊天控制器
 */
@SaCheckLogin
@RestController
@RequestMapping("/system/chat")
public class SystemChatController {

    @Resource
    private SystemChatService chatService;

    /**
     * 发送消息（当前登录用户 -> 指定用户）
     */
    @PostMapping("/send")
    public Result<SystemChatMessageVO> send(@Validated @RequestBody SystemChatSendMessageForm form) {
        Object loginId = StpUtil.getLoginId();
        Long fromUserId = Long.parseLong(loginId.toString());
        return chatService.sendMessage(fromUserId, form);
    }

    /**
     * 分页获取与某个用户的聊天记录
     */
    @GetMapping("/history")
    public Result<BasePageVO<SystemChatMessageVO>> history(@RequestParam Long peerUserId,
                                                           @RequestParam(defaultValue = "1") Integer current,
                                                           @RequestParam(defaultValue = "20") Integer size) {
        return chatService.getChatHistory(peerUserId, current, size);
    }

    /**
     * 获取当前用户未读消息数量
     */
    @GetMapping("/unread-count")
    public Result<Long> unreadCount() {
        return chatService.getUnreadCount();
    }

    /**
     * 获取当前用户的聊天列表
     */
    @GetMapping("/list")
    public Result<List<SystemChatVO>> list() {
        return chatService.getChatList();
    }
}


