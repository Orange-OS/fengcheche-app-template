package com.zsy.module.system.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.constant.CacheKeyConst;
import com.zsy.framework.common.exception.BusinessException;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.permission.domain.entity.SystemPermissionEntity;
import com.zsy.module.system.permission.mapper.SystemPermissionMapper;
import com.zsy.module.system.permission.service.SystemPermissionService;
import com.zsy.module.system.rolepermission.domain.SystemRolePermissionEntity;
import com.zsy.module.system.rolepermission.mapper.SystemRolePermissionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统权限 Service 实现类
 */
@Service
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionMapper, SystemPermissionEntity> implements SystemPermissionService {

    @Resource
    private SystemRolePermissionMapper systemRolePermissionMapper;

    @Override
    public List<SystemPermissionEntity> getPermissionsByRoleId(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    @Cacheable(value = CacheKeyConst.USER_PERMISSIONS, key = "#userId")
    public List<SystemPermissionEntity> getPermissionsByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    @Cacheable(value = CacheKeyConst.USER_PERMISSION_CODES, key = "#userId")
    public List<String> getPermissionCodesByUserId(Long userId) {
        List<SystemPermissionEntity> permissions = getPermissionsByUserId(userId);
        return permissions.stream()
                .map(SystemPermissionEntity::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPermissionAssigned(Long permissionId) {
        LambdaQueryWrapper<SystemRolePermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemRolePermissionEntity::getPermissionId, permissionId);
        return systemRolePermissionMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public List<SystemPermissionEntity> getPermissionsByMenuId(Long menuId) {
        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemPermissionEntity::getMenuId, menuId);
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
    public boolean batchSavePermissions(List<SystemPermissionEntity> permissions) {
        return saveBatch(permissions);
    }

    /**
     * 更新权限状态
     *
     * @param permissionId 权限ID
     * @param status       状态(0-禁用,1-正常)
     */
    @Override
    public void updatePermissionStatus(Long permissionId, Integer status) {
        SystemPermissionEntity permission = this.getById(permissionId);
        if (permission == null) {
            throw BusinessException.of("权限不存在");
        }

        permission.setStatus(status);
        this.updateById(permission);
    }


    public List<SystemMenuEntity> buildPermissionTree(List<SystemMenuEntity> menus, List<SystemPermissionEntity> permissions) {
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
    public List<SystemPermissionEntity> getPermissionTree() {
        return null;
    }

    @Override
    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
    public boolean save(SystemPermissionEntity entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
    public boolean updateById(SystemPermissionEntity entity) {
        return super.updateById(entity);
    }

    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
    public boolean removeById(SystemPermissionEntity entity) {
        return super.removeById(entity);
    }
} 