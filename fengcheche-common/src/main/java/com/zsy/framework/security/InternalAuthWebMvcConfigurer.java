package com.zsy.framework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注册服务间内部调用鉴权拦截器。
 */
@Configuration
public class InternalAuthWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private InternalAuthProperties internalAuthProperties;

    @Resource
    private InternalAuthInterceptor internalAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includePaths = internalAuthProperties.getIncludePaths();
        if (includePaths == null || includePaths.isEmpty()) {
            return;
        }

        registry.addInterceptor(internalAuthInterceptor)
                .addPathPatterns(includePaths)
                .excludePathPatterns(internalAuthProperties.getExcludePaths());
    }
}
