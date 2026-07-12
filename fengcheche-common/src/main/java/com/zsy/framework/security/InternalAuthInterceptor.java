package com.zsy.framework.security;

import com.zsy.common.constant.RequestHeaderConst;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务间内部调用鉴权拦截器。
 */
@Component
public class InternalAuthInterceptor implements HandlerInterceptor {

    @Resource
    private InternalAuthProperties internalAuthProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!internalAuthProperties.isEnabled()) {
            return true;
        }

        String configuredToken = internalAuthProperties.getToken();
        if (!StringUtils.hasText(configuredToken)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal auth token is not configured");
            return false;
        }

        String requestToken = request.getHeader(RequestHeaderConst.INTERNAL_TOKEN);
        if (configuredToken.equals(requestToken)) {
            return true;
        }

        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid internal token");
        return false;
    }
}
