package com.zsy.module.system.login.service;

import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.login.domain.vo.LoginResultVO;
import com.zsy.module.system.login.domain.form.SystemUserLoginForm;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;
import com.zsy.module.system.user.domain.SystemUserEntity;

import java.util.List;

/**
 * 登录服务接口
 */
public interface LoginService {
    
    /**
     * 用户登录
     * @return 登录结果
     */
    Result<LoginResultVO> login(SystemUserLoginForm systemUserLoginForm);
    
    /**
     * 用户登出
     */
    void logout();
    
    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    SystemUserEntity getUserInfo();
    
    /**
     * 获取用户菜单
     * @return 菜单列表
     */
    List<SystemMenuEntity> getUserMenuList();
    
    /**
     * 获取用户权限
     * @return 权限列表
     */
    List<SystemPermissionEntity> getUserPermissionList();
}
