package com.zsy.module.system.userrole.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.userrole.domain.vo.SystemUserRoleVO;
import com.zsy.module.system.userrole.service.SystemUserRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/system/user-role")
public class SystemUserRoleController {
    @Resource
    private SystemUserRoleService systemUserRoleService;


    /**
     * 获取用户角色列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:user:role:query")
    public Result<List<SystemUserRoleVO>> getUserRoleList(@RequestParam(required = false, value = "username") String username) {
        return Result.success(systemUserRoleService.getUserRoleList(username));
    }
}
