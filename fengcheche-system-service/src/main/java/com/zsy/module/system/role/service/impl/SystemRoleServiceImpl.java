package com.zsy.module.system.role.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.constant.CacheKeyConst;
import com.zsy.module.system.role.domain.entity.SystemRoleEntity;
import com.zsy.module.system.role.domain.form.SystemRoleAddForm;
import com.zsy.module.system.role.domain.form.SystemRoleUpdateForm;
import com.zsy.module.system.role.domain.vo.SystemRoleVO;
import com.zsy.module.system.role.mapper.SystemRoleMapper;
import com.zsy.module.system.role.service.SystemRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色 Service 实现类
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRoleEntity> implements SystemRoleService {
    
    @Override
    @Cacheable(value = CacheKeyConst.USER_ROLES, key = "#userId")
    public List<SystemRoleEntity> getRolesByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
    
    @Override
    @Cacheable(value = CacheKeyConst.USER_ROLE_CODES, key = "#userId")
    public List<String> getRoleCodesByUserId(Long userId) {
        List<SystemRoleEntity> roles = getRolesByUserId(userId);
        return roles.stream()
                .map(SystemRoleEntity::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public Page<SystemRoleVO> page(Integer pageNum, Integer pageSize, String name, String code) {
        Page<SystemRoleEntity> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<SystemRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), SystemRoleEntity::getName, name);
        wrapper.like(StrUtil.isNotBlank(code), SystemRoleEntity::getCode, code);
        wrapper.orderByAsc(SystemRoleEntity::getSort);

        page = page(page, wrapper);

        // 转换为DTO
        List<SystemRoleVO> records = page.getRecords().stream().map(record -> {
            SystemRoleVO systemRoleVO = new SystemRoleVO();
            BeanUtils.copyProperties(record, systemRoleVO);
            return systemRoleVO;
        }).collect(Collectors.toList());

        Page<SystemRoleVO> systemRoleVOPage = new Page<>();
        BeanUtils.copyProperties(page, systemRoleVOPage);
        systemRoleVOPage.setRecords(records);

        return systemRoleVOPage;
    }

    @Override
    public void create(SystemRoleAddForm addForm) {
        SystemRoleEntity systemRoleEntity = new SystemRoleEntity();
        BeanUtils.copyProperties(addForm, systemRoleEntity);

        systemRoleEntity.setCreateBy(StpUtil.getLoginIdAsLong());
        systemRoleEntity.setCreateTime(LocalDateTime.now());

        save(systemRoleEntity);
    }

    @Override
    public void update(SystemRoleUpdateForm updateForm) {
        SystemRoleEntity systemRoleEntity = getById(updateForm.getId());
        if (systemRoleEntity == null) {
            throw new RuntimeException("角色不存在");
        }

        BeanUtils.copyProperties(updateForm, systemRoleEntity);
        systemRoleEntity.setUpdateBy(StpUtil.getLoginIdAsLong());
        systemRoleEntity.setUpdateTime(LocalDateTime.now());

        updateById(systemRoleEntity);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }
} 
