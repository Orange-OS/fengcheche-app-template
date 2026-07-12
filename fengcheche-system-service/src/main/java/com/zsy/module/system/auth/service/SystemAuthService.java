package com.zsy.module.system.auth.service;

import com.zsy.common.domain.Result;
import com.zsy.module.system.auth.domain.form.SystemUserLoginForm;
import com.zsy.module.system.auth.domain.vo.LoginResultVO;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;

import java.util.List;

/**
 * 登录服务接口
 */
public interface SystemAuthService {

    /**
     * 用户登录
     *
     * @return 登录结果
     */
    Result<LoginResultVO> login(SystemUserLoginForm systemUserLoginForm);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    SystemUserEntity getUserInfo();

    /**
     * 获取用户菜单
     *
     * @return 菜单列表
     */
    List<SystemMenuEntity> getUserMenuList();

    /**
     * 获取用户权限
     *
     * @return 权限列表
     */
    List<SystemMenuEntity> getUserPermissionList();
}
