package com.zsy.framework.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 指定一条拦截规则：拦截所有 path
            SaRouter.match("/**")
                    // 排除登录接口和静态资源
                    .notMatch(
                            "/system/auth/login",             // 登录接口
                            "/system-captcha/generate",                // 验证码接口
                            "/system/user/register",
                            "/internal/**",                   // 内部服务调用接口，交给内部令牌校验
                            "/doc.html",
                            "/webjars/**",
                            "/swagger-ui/**", "/swagger-resources/**" // swagger
                    )
                    // 检查是否登录
                    .check(r -> StpUtil.checkLogin());

            // 权限认证规则
            SaRouter.match("/**", r -> {
                // 用户有 admin 角色
                if (StpUtil.hasRole("admin")) {
                    // 停止后续的匹配检查
                    SaRouter.stop();
                }
            });
        })).addPathPatterns("/**");

    }

    /**
     * 全局CORS配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 使用 allowedOriginPatterns 代替 allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowCredentials(true) // 允许携带凭证
                .maxAge(3600); // 预检请求缓存时间
    }
} 
