package com.zsy.module.system.user.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.common.domain.Result;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.domain.form.SystemUserCreateForm;
import com.zsy.module.system.user.domain.form.SystemUserUpdateForm;
import com.zsy.module.system.user.mapper.SystemUserMapper;
import com.zsy.module.system.userrole.domain.entity.SystemUserRoleEntity;
import com.zsy.module.system.userrole.mapper.SystemUserRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import cn.dev33.satoken.secure.SaSecureUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserEntity> implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> createUser(SystemUserCreateForm createForm) {
        // 检查用户名是否已存在
        if (checkUsername(createForm.getUsername())) {
            return Result.warning("用户名已存在");
        }

        // 创建用户
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        BeanUtils.copyProperties(createForm, systemUserEntity);
        // 密码加密 - 保持原有方式
        systemUserEntity.setPassword(SaSecureUtil.md5(systemUserEntity.getPassword()));
        systemUserMapper.insert(systemUserEntity);

        // 创建用户角色关系
        createUserRoleRelations(systemUserEntity.getId(), createForm.getRoleIds());

        return Result.success();
    }

    /**
     * 创建用户角色关系
     */
    private void createUserRoleRelations(Long userId, List<Long> roleIds) {
        List<SystemUserRoleEntity> userRoleEntities = roleIds.stream()
                .map(roleId -> {
                    SystemUserRoleEntity entity = new SystemUserRoleEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    return entity;
                })
                .collect(Collectors.toList());

        // 批量插入用户角色关系
        systemUserRoleMapper.insert(userRoleEntities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SystemUserUpdateForm updateForm) {
        // 保存用户
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        BeanUtils.copyProperties(updateForm, systemUserEntity);
        // 不允许修改密码
        systemUserEntity.setPassword(null);
        this.updateById(systemUserEntity);

        // 更新用户角色关系
        updateUserRoleRelations(updateForm.getId(), updateForm.getRoleIds());
    }

    /**
     * 更新用户角色关系
     */
    private void updateUserRoleRelations(Long userId, List<Long> newRoleIds) {
        // 删除用户现有的所有角色关系
        systemUserRoleMapper.deleteByUserId(userId);

        // 创建新的角色关系
        createUserRoleRelations(userId, newRoleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 删除用户
        this.removeById(id);

        // 删除用户现有的所有角色关系
        systemUserRoleMapper.deleteByUserId(id);
    }

    @Override
    public Result<Void> updateUserStatus(Long id, Integer status) {
        SystemUserEntity user = this.getById(id);
        if (user == null) {
            return Result.warning("用户不存在");
        }

        user.setStatus(status);
        this.updateById(user);

        return Result.success();
    }

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
    public Result<Void> changePassword(Long userId, String oldPassword, String newPassword) {
        SystemUserEntity user = this.getById(userId);
        if (user == null) {
            return Result.warning("用户不存在");
        }

        // 验证旧密码
        if (!user.getPassword().equals(SaSecureUtil.md5(oldPassword))) {
            return Result.warning("原密码错误");
        }

        // 更新密码
        user.setPassword(SaSecureUtil.md5(newPassword));
        this.updateById(user);

        return Result.success();
    }

    @Override
    public Result<Void> resetPassword(Long id) {
        SystemUserEntity user = this.getById(id);
        if (user == null) {
            return Result.warning("用户不存在");
        }

        // 重置为默认密码
        user.setPassword(SaSecureUtil.md5("123456"));
        this.updateById(user);

        return Result.success();
    }

//    @Override
//    public Boolean checkUsername(String username) {
//        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemUserEntity::getUsername, username);
//        return count(queryWrapper) > 0;
//    }

    public Boolean checkUser(Long id) {
        return systemUserMapper.selectById(id) != null;
    }

    public Boolean checkUsername(String username) {
        return systemUserMapper.selectByUsername(username) != null;
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


} 