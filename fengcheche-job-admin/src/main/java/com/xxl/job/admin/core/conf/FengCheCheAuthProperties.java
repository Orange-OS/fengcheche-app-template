package com.xxl.job.admin.core.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本系统鉴权接入配置。
 * XXL-JOB Admin 不注册到 Nacos，只通过 HTTP 调用系统服务校验 Sa-Token。
 */
@Component
@ConfigurationProperties(prefix = "fengcheche.auth")
public class FengCheCheAuthProperties {

    /**
     * 是否启用本系统登录态接入。
     */
    private boolean enabled = true;

    /**
     * 系统鉴权接口基础地址，默认走网关路由到 system-service。
     */
    private String systemAuthBaseUrl = "http://127.0.0.1:9000/api/system/auth";

    /**
     * 本系统 Sa-Token 请求头名称。
     */
    private String tokenName = "Authorization";

    /**
     * 本系统 Sa-Token 前缀。
     */
    private String tokenPrefix = "Bearer";

    /**
     * 允许进入 XXL-JOB Admin 的系统角色。
     */
    private String requiredRole = "admin";

    /**
     * 系统鉴权通过后映射到 XXL-JOB Admin 内置账号。
     */
    private String xxlAdminUsername = "admin";

    /**
     * 是否持久化 XXL-JOB Admin 登录 Cookie。
     */
    private boolean rememberXxlCookie = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSystemAuthBaseUrl() {
        return systemAuthBaseUrl;
    }

    public void setSystemAuthBaseUrl(String systemAuthBaseUrl) {
        this.systemAuthBaseUrl = systemAuthBaseUrl;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getRequiredRole() {
        return requiredRole;
    }

    public void setRequiredRole(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    public String getXxlAdminUsername() {
        return xxlAdminUsername;
    }

    public void setXxlAdminUsername(String xxlAdminUsername) {
        this.xxlAdminUsername = xxlAdminUsername;
    }

    public boolean isRememberXxlCookie() {
        return rememberXxlCookie;
    }

    public void setRememberXxlCookie(boolean rememberXxlCookie) {
        this.rememberXxlCookie = rememberXxlCookie;
    }
}
