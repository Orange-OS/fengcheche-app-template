package com.zsy.module.system.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.framework.common.exception.BusinessException;
import com.zsy.module.system.user.domain.SystemUserEntity;
import com.zsy.module.system.user.mapper.SystemUserMapper;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.stereotype.Service;
import cn.dev33.satoken.secure.SaSecureUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserEntity> implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public List<SystemUserEntity> getUserList(String username) {
        List<SystemUserEntity> users = this.list(new LambdaQueryWrapper<SystemUserEntity>()
                .like(StrUtil.isNotBlank(username), SystemUserEntity::getUsername, username)
        );
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public Page<SystemUserEntity> getUserPage(Integer current, Integer size, SystemUserEntity queryParams) {
        Page<SystemUserEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();

        if (queryParams.getUsername() != null) {
            queryWrapper.like(SystemUserEntity::getUsername, queryParams.getUsername());
        }
        if (queryParams.getNickname() != null) {
            queryWrapper.like(SystemUserEntity::getNickname, queryParams.getNickname());
        }
        if (queryParams.getStatus() != null) {
            queryWrapper.eq(SystemUserEntity::getStatus, queryParams.getStatus());
        }

        this.page(page, queryWrapper);
        page.getRecords().forEach(user -> user.setPassword(null));

        return page;
    }

    @Override
    public SystemUserEntity getUserById(Long id) {
        SystemUserEntity user = this.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public void createUser(SystemUserEntity user) {
        // 检查用户名是否已存在
        if (this.checkUsername(user.getUsername())) {
            throw BusinessException.of("用户名已存在");
        }

        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认启用
        }

        // 密码加密
        user.setPassword(SaSecureUtil.md5(user.getPassword()));

        this.save(user);
    }

    @Override
    public void updateUser(SystemUserEntity user) {
        // 不允许修改密码
        user.setPassword(null);
        this.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.removeById(id);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SystemUserEntity user = this.getById(userId);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        // 验证旧密码
        if (!user.getPassword().equals(SaSecureUtil.md5(oldPassword))) {
            throw BusinessException.of("原密码错误");
        }

        // 更新密码
        user.setPassword(SaSecureUtil.md5(newPassword));
        this.updateById(user);
    }

    @Override
    public void resetPassword(Long id) {
        SystemUserEntity user = this.getById(id);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        // 重置为默认密码
        user.setPassword(SaSecureUtil.md5("123456"));
        this.updateById(user);
    }

    @Override
    public Boolean checkUsername(String username) {
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUserEntity::getUsername, username);
        return count(queryWrapper) > 0;
    }

    @Override
    public SystemUserEntity getByUsername(String username) {
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUserEntity::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public SystemUserEntity selectByUsername(String username) {
        return systemUserMapper.selectByUsername(username);
    }

    @Override
    public SystemUserEntity selectById(Long id) {
        return systemUserMapper.selectById(id);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        SystemUserEntity user = this.getById(id);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        user.setStatus(status);
        this.updateById(user);
    }
} 