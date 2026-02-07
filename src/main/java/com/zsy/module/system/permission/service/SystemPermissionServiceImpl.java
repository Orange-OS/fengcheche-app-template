package com.zsy.module.system.permission.service;

import cn.hutool.core.bean.BeanUtil;
import com.zsy.constant.CacheKeyConst;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.domain.vo.MenuVO;
import com.zsy.module.system.menu.mapper.SystemMenuMapper;
import com.zsy.module.system.permission.domain.vo.PermissionTreeVO;
import com.zsy.module.system.role.domain.entity.SystemRoleEntity;
import com.zsy.module.system.role.mapper.SystemRoleMapper;
import com.zsy.module.system.rolemenu.domain.entity.SystemRoleMenuEntity;
import com.zsy.module.system.rolemenu.mapper.SystemRoleMenuMapper;
import com.zsy.module.system.userrole.mapper.SystemUserRoleMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统权限 Service 实现类
 */
@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    @Resource
    private SystemRoleMapper systemRoleMapper;

    @Resource
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Resource
    private SystemMenuMapper systemMenuMapper;


    //    @Override
//    public List<SystemPermissionEntity> getPermissionsByRoleId(Long roleId) {
//        return baseMapper.selectByRoleId(roleId);
//    }
//
    @Override
    @Cacheable(value = CacheKeyConst.USER_PERMISSIONS, key = "#userId")
    public List<SystemMenuEntity> getPermissionsByUserId(Long userId) {
        return systemRoleMenuMapper.selectListByUserId(userId);
    }

    @Override
    @Cacheable(value = CacheKeyConst.USER_PERMISSION_CODES, key = "#userId")
    public List<String> getPermissionCodesByUserId(Long userId) {
        List<SystemMenuEntity> systemMenuEntityList = systemRoleMenuMapper.queryPermissionsByUserId(userId);
        return CollectionUtils.isEmpty(systemMenuEntityList) ? Collections.emptyList() :
                systemMenuEntityList.stream()
                        .map(SystemMenuEntity::getPermission)
                        .filter(Objects::nonNull)
                        .filter(StringUtils::hasLength)
                        .collect(Collectors.toList());
    }

//    @Override
//    public boolean isPermissionAssigned(Long permissionId) {
////        LambdaQueryWrapper<SystemRolePermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
////        queryWrapper.eq(SystemRolePermissionEntity::getPermissionId, permissionId);
////        return systemRolePermissionMapper.selectCount(queryWrapper) > 0;
//        return false;
//    }

//    @Override
//    public List<SystemPermissionEntity> getPermissionsByMenuId(Long menuId) {
//        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemPermissionEntity::getMenuId, menuId);
//        return list(queryWrapper);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
//    public boolean batchSavePermissions(List<SystemPermissionEntity> permissions) {
//        return saveBatch(permissions);
//    }

//    /**
//     * 更新权限状态
//     *
//     * @param permissionId 权限ID
//     * @param status       状态(0-禁用,1-正常)
//     */
//    @Override
//    public void updatePermissionStatus(Long permissionId, Integer status) {
//        SystemPermissionEntity permission = this.getById(permissionId);
//        if (permission == null) {
//            throw BusinessException.of("权限不存在");
//        }
//
//        permission.setStatus(status);
//        this.updateById(permission);
//    }


    //    public List<SystemMenuEntity> buildPermissionTree(List<SystemMenuEntity> menus, List<SystemPermissionEntity> permissions) {
//        // 构建菜单ID与菜单对象的映射关系
//        Map<Long, SystemMenuEntity> menuMap = menus.stream()
//                .collect(Collectors.toMap(SystemMenuEntity::getId, menu -> menu));
//
//        List<SystemMenuEntity> menuTree = new ArrayList<>();
//
//        // 遍历菜单列表，构建菜单树
//        for (SystemMenuEntity menu : menus) {
//            // 如果是根菜单（父ID为0或null），则直接添加到结果列表
//            if (menu.getParentId() == null || menu.getParentId() == 0L) {
//                menuTree.add(menu);
//                continue;
//            }
//
//            // 获取父菜单
//            SystemMenuEntity parentMenu = menuMap.get(menu.getParentId());
//            if (parentMenu != null) {
//                // 如果父菜单的子菜单列表为空，则初始化
//                if (parentMenu.getChildren() == null) {
//                    parentMenu.setChildren(new ArrayList<>());
//                }
//                // 将当前菜单添加到父菜单的子菜单列表
//                parentMenu.getChildren().add(menu);
//            } else {
//                // 如果找不到父菜单，则作为根菜单处理
//                menuTree.add(menu);
//            }
//        }
//
//        return menuTree;
//    }
    @Override
    public PermissionTreeVO getPermissionTree(Long roleId) {

        // 查询角色ID对应权限
        List<Long> permissionIds = systemRoleMenuMapper.queryPermissionIdsByRoleId(roleId);

        // 查询菜单
        List<SystemMenuEntity> systemMenuEntityList = systemMenuMapper.queryMenuList(1, null);
        Map<Long, List<SystemMenuEntity>> parentMap = systemMenuEntityList.stream().collect(Collectors.groupingBy(SystemMenuEntity::getParentId, Collectors.toList()));
        List<MenuVO> menuTree = this.buildMenuTree(parentMap, NumberUtils.LONG_ZERO);

        // 返回
        PermissionTreeVO vo = new PermissionTreeVO();
        vo.setRoleId(roleId);
        vo.setPermissionTree(menuTree);
        vo.setSelectedPermissionIdList(permissionIds);
        return vo;
    }

    /**
     * 更新权限树
     *
     * @param roleId     角色ID
     * @param menuIdList 菜单ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermissionTree(Long roleId, List<Long> menuIdList) {

        // 1. 验证角色是否存在
        SystemRoleEntity role = systemRoleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("角色不存在，ID: " + roleId);
        }

        // 2. 验证菜单ID列表的有效性（可选）
        if (menuIdList != null && !menuIdList.isEmpty()) {
            List<SystemMenuEntity> existingMenus = systemMenuMapper.selectBatchIds(menuIdList);
            if (existingMenus.size() != menuIdList.size()) {
                throw new RuntimeException("部分菜单ID不存在");
            }
        }

        // 3. 开启事务（如果方法上没有@Transactional注解）
        // 删除原有权限
        systemRoleMenuMapper.deleteByRoleId(roleId);

        // 4. 插入新的权限
        if (menuIdList != null && !menuIdList.isEmpty()) {
            List<SystemRoleMenuEntity> systemRoleMenuEntityList = menuIdList.stream()
                    .map(menuId -> {
                        SystemRoleMenuEntity systemRoleMenuEntity = new SystemRoleMenuEntity();
                        systemRoleMenuEntity.setRoleId(roleId);
                        systemRoleMenuEntity.setMenuId(menuId);
                        return systemRoleMenuEntity;
                    })
                    .collect(Collectors.toList());

            systemRoleMenuEntityList.forEach(item -> systemRoleMenuMapper.insert(item));
        }
    }

    /**
     * 构建菜单树
     */
    private List<MenuVO> buildMenuTree(Map<Long, List<SystemMenuEntity>> parentMap, Long parentId) {
        // 获取本级菜单树List
        List<MenuVO> res = parentMap.getOrDefault(parentId, Collections.emptyList()).stream()
                .map(e -> BeanUtil.copyProperties(e, MenuVO.class))
                .collect(Collectors.toList());
        // 循环遍历下级菜单
        res.forEach(e -> {
            e.setChildren(this.buildMenuTree(parentMap, e.getId()));
        });
        return res;
    }

//    @Override
//    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
//    public boolean save(SystemPermissionEntity entity) {
//        return super.save(entity);
//    }
//
//    @Override
//    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
//    public boolean updateById(SystemPermissionEntity entity) {
//        return super.updateById(entity);
//    }
//
//    @CacheEvict(value = {CacheKeyConst.USER_PERMISSIONS, CacheKeyConst.USER_PERMISSION_CODES}, allEntries = true)
//    public boolean removeById(SystemPermissionEntity entity) {
//        return super.removeById(entity);
//    }
} 