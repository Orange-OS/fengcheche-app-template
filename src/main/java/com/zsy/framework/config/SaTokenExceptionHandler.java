package com.zsy.framework.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.zsy.framework.common.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sa-Token 全局异常处理器
 */
@RestControllerAdvice
public class SaTokenExceptionHandler {
    
    /**
     * 处理未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<Void> handleNotLoginException(NotLoginException e) {
        return Result.error(401, "未登录或登录已过期");
    }
    
    /**
     * 处理无权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result<Void> handleNotPermissionException(NotPermissionException e) {
        return Result.error(403, "无此权限：" + e.getPermission());
    }
    
    /**
     * 处理无角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<Void> handleNotRoleException(NotRoleException e) {
        return Result.error(403, "无此角色：" + e.getRole());
    }
} 