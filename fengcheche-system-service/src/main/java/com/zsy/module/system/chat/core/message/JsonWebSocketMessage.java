package com.zsy.module.system.chat.core.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * JSON 格式的 WebSocket 消息帧
 */
@Data
@Accessors(chain = true) // 关键注解
public class JsonWebSocketMessage implements Serializable {

    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息内容
     * 要求 JSON 对象
     */
    private String content;
}
