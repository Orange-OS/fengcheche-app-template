package com.zsy.common.constant;

/**
 * 请求消息头常量
 */
public class RequestHeaderConst {
    public static final String TOKEN = "x-access-token";

    public static final String USER_AGENT = "user-agent";

    /**
     * 服务间内部调用令牌请求头。
     */
    public static final String INTERNAL_TOKEN = "X-Internal-Token";

    /**
     * 发起内部调用的服务名请求头，便于排查调用来源。
     */
    public static final String INTERNAL_SERVICE = "X-Internal-Service";
}
