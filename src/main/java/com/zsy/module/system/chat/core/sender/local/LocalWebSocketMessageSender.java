package com.zsy.module.system.chat.core.sender.local;

import com.zsy.module.system.chat.core.sender.AbstractWebSocketMessageSender;
import com.zsy.module.system.chat.core.sender.WebSocketMessageSender;
import com.zsy.module.system.chat.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 * <p>
 * 注意：仅仅适合单机场景！！！
 *
 * @author 芋道源码
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
