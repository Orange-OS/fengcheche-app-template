package com.zsy.module.system.menu.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.constant.CacheKeyConst;
import com.zsy.framework.common.exception.BusinessException;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.domain.form.SystemMenuAddForm;
import com.zsy.module.system.menu.domain.form.SystemMenuUpdateForm;
import com.zsy.module.system.menu.mapper.SystemMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统菜单 Service 实现类
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenuEntity> implements SystemMenuService {

    @Override
    public List<SystemMenuEntity> getMenusByRoleId(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    @Cacheable(value = CacheKeyConst.USER_MENUS, key = "#userId")
    public List<SystemMenuEntity> getMenusByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public List<SystemMenuEntity> buildMenuTree(List<SystemMenuEntity> menus) {
        // 构建菜单ID与菜单对象的映射关系
        Map<Long, SystemMenuEntity> menuMap = menus.stream()
                .collect(Collectors.toMap(SystemMenuEntity::getId, menu -> menu));

        List<SystemMenuEntity> menuTree = new ArrayList<>();

        // 遍历菜单列表，构建菜单树
        for (SystemMenuEntity menu : menus) {
            // 如果是根菜单（父ID为0或null），则直接添加到结果列表
            if (menu.getParentId() == null || menu.getParentId() == 0L) {
                menuTree.add(menu);
                continue;
            }

            // 获取父菜单
            SystemMenuEntity parentMenu = menuMap.get(menu.getParentId());
            if (parentMenu != null) {
                // 如果父菜单的子菜单列表为空，则初始化
                if (parentMenu.getChildren() == null) {
                    parentMenu.setChildren(new ArrayList<>());
                }
                // 将当前菜单添加到父菜单的子菜单列表
                parentMenu.getChildren().add(menu);
            } else {
                // 如果找不到父菜单，则作为根菜单处理
                menuTree.add(menu);
            }
        }

        return menuTree;
    }

    @Override
    @Cacheable(value = CacheKeyConst.USER_MENU_TREE, key = "#userId")
    public List<SystemMenuEntity> getMenuTreeByUserId(Long userId) {
        List<SystemMenuEntity> menus = getMenusByUserId(userId);
        return buildMenuTree(menus);
    }

    @Override
    public Page<SystemMenuEntity> page(Integer current, Integer size, SystemMenuEntity menu) {
        Page<SystemMenuEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<SystemMenuEntity> queryWrapper = new LambdaQueryWrapper<>();

        // 根据条件构建查询
        if (menu.getName() != null) {
            queryWrapper.like(SystemMenuEntity::getName, menu.getName());
        }
        if (menu.getStatus() != null) {
            queryWrapper.eq(SystemMenuEntity::getStatus, menu.getStatus());
        }

        // 默认按ID排序
        queryWrapper.orderByAsc(SystemMenuEntity::getId);

        return this.page(page, queryWrapper);
    }

    @Override
    public void create(SystemMenuAddForm addForm) {
        SystemMenuEntity systemMenuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(addForm, systemMenuEntity);

        systemMenuEntity.setCreateBy(StpUtil.getLoginIdAsLong());
        systemMenuEntity.setCreateTime(LocalDateTime.now());

        save(systemMenuEntity);
    }

    @Override
    public void update(SystemMenuUpdateForm updateForm) {
        SystemMenuEntity systemMenuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(updateForm, systemMenuEntity);

        systemMenuEntity.setUpdateBy(StpUtil.getLoginIdAsLong());

        // 更新
        updateById(systemMenuEntity);
    }

    @Override
    public void delete(Long id) {
        SystemMenuEntity systemMenuEntity = baseMapper.selectById(id);

        if (systemMenuEntity != null) {
            // 删除角色菜单关联
//            systemRoleMenuMapper.deleteByMenuId(id);

            // 删除菜单
            baseMapper.deleteById(systemMenuEntity);
        }
    }

    @Override
    public void updateMenuStatus(Long id, Integer status) {
        SystemMenuEntity systemMenuEntity = this.getById(id);
        if (systemMenuEntity == null) {
            throw BusinessException.of("菜单不存在");
        }

        systemMenuEntity.setStatus(status);
        this.updateById(systemMenuEntity);
    }

} 