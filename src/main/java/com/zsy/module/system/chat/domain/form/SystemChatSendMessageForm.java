package com.zsy.module.system.chat.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发送聊天消息表单
 */
@Data
public class SystemChatSendMessageForm implements Serializable {

    /**
     * 接收方用户ID
     */
    @NotNull(message = "接收方用户ID不能为空")
    private Long toUserId;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;
}


