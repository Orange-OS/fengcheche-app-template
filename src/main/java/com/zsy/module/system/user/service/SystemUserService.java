package com.zsy.module.system.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.common.domain.Result;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.domain.form.SystemUserCreateForm;
import com.zsy.module.system.user.domain.form.SystemUserUpdateForm;

import java.util.List;

public interface SystemUserService extends IService<SystemUserEntity> {

    /**
     * 创建用户
     */
    Result<Void> createUser(SystemUserCreateForm createForm);

    /**
     * 获取用户列表（已脱敏）
     */
    List<SystemUserEntity> getUserList(String username);

    /**
     * 分页查询用户（已脱敏）
     */
    Page<SystemUserEntity> getUserPage(Integer current, Integer size, SystemUserEntity queryParams);

    /**
     * 获取用户详情（已脱敏）
     */
    SystemUserEntity getUserById(Long id);

    /**
     * 更新用户信息
     */
    void updateUser(SystemUserUpdateForm updateForm);

    /**
     * 删除用户
     */
    void deleteUser(Long id);

    /**
     * 修改用户密码
     */
    Result<Void> changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 重置用户密码
     */
    Result<Void> resetPassword(Long id);

    /**
     * 更新用户状态
     */
    Result<Void> updateUserStatus(Long id, Integer status);

    /**
     * 检查用户名是否存在
     */
    Boolean checkUsername(String username);

    /**
     * 根据用户名查询用户
     */
    SystemUserEntity getByUsername(String username);

    /**
     * 根据用户名查询用户
     */
    SystemUserEntity selectByUsername(String username);

    /**
     * 根据ID查询用户
     */
    SystemUserEntity selectById(Long id);
}
