package com.zsy.framework.feign;

import com.zsy.common.constant.RequestHeaderConst;
import com.zsy.framework.security.InternalAuthProperties;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Feign 服务间调用公共配置。
 */
@Configuration
public class FengCheCheFeignConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Resource
    private InternalAuthProperties internalAuthProperties;

    @Value("${spring.application.name:unknown-service}")
    private String applicationName;

    @Bean
    public RequestInterceptor internalTokenFeignInterceptor() {
        return template -> {
            // 服务间 Feign 调用统一携带内部令牌，下游 /internal/** 接口据此校验调用来源。
            String internalToken = internalAuthProperties.getToken();
            if (StringUtils.hasText(internalToken)) {
                template.header(RequestHeaderConst.INTERNAL_TOKEN, internalToken);
            }
            template.header(RequestHeaderConst.INTERNAL_SERVICE, applicationName);

            // 业务需要识别当前登录用户时，继续向下游透传用户登录 token。
            String authorization = getCurrentRequestHeader(AUTHORIZATION_HEADER);
            if (StringUtils.hasText(authorization)) {
                template.header(AUTHORIZATION_HEADER, authorization);
            }
        };
    }

    private String getCurrentRequestHeader(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request.getHeader(headerName);
    }
}
