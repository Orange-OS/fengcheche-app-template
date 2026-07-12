package com.zsy.framework.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务间内部调用鉴权配置。
 */
@Component
@ConfigurationProperties(prefix = "fengcheche.security.internal")
public class InternalAuthProperties {

    /**
     * 是否开启内部调用鉴权。
     */
    private boolean enabled = true;

    /**
     * 内部调用共享令牌，调用方和被调用方必须保持一致。
     */
    private String token;

    /**
     * 需要内部鉴权保护的路径。
     */
    private List<String> includePaths = new ArrayList<String>() {{
        add("/internal/**");
    }};

    /**
     * 内部鉴权排除路径。
     */
    private List<String> excludePaths = new ArrayList<String>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = includePaths;
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }
}
