package com.zsy.module.system.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.zsy.framework.common.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理员控制器
 */
@RestController
@RequestMapping("/system/admin")
public class SystemAdminController {
    
    /**
     * 管理员首页
     */
    @SaCheckRole("admin")
    @GetMapping("/index")
    public Result<String> index() {
        return Result.success("管理员访问成功");
    }
    
    /**
     * 用户管理页面
     */
    @SaCheckRole("admin")
    @GetMapping("/users")
    public Result<String> userManagement() {
        return Result.success("用户管理页面访问成功");
    }
} 