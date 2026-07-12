//package com.zsy.config;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.zsy.constant.PermissionTypeConst;
//import com.zsy.module.system.permission.domain.SystemPermissionEntity;
//import com.zsy.module.system.permission.service.SystemPermissionService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 权限数据初始化
// */
//@Slf4j
//@Component
//public class PermissionInitializer implements CommandLineRunner {
//
//    @Resource
//    private SystemPermissionService systemPermissionService;
//
//    @Override
//    public void run(String... args) {
//        initBasePermissions();
//    }
//
//    /**
//     * 初始化基本权限
//     */
//    private void initBasePermissions() {
//        log.info("开始初始化基本权限...");
//
//        List<SystemPermissionEntity> permissions = new ArrayList<>();
//
//        // 用户管理权限
//        addPermissionIfNotExist(permissions, "用户查询", "system:user:query", PermissionTypeConst.API, "用户查询权限");
//        addPermissionIfNotExist(permissions, "用户创建", "system:user:create", PermissionTypeConst.API, "用户创建权限");
//        addPermissionIfNotExist(permissions, "用户修改", "system:user:update", PermissionTypeConst.API, "用户修改权限");
//        addPermissionIfNotExist(permissions, "用户删除", "system:user:delete", PermissionTypeConst.API, "用户删除权限");
//
//        // 角色管理权限
//        addPermissionIfNotExist(permissions, "角色查询", "system:role:query", PermissionTypeConst.API, "角色查询权限");
//        addPermissionIfNotExist(permissions, "角色创建", "system:role:create", PermissionTypeConst.API, "角色创建权限");
//        addPermissionIfNotExist(permissions, "角色修改", "system:role:update", PermissionTypeConst.API, "角色修改权限");
//        addPermissionIfNotExist(permissions, "角色删除", "system:role:delete", PermissionTypeConst.API, "角色删除权限");
//
//        // 菜单管理权限
//        addPermissionIfNotExist(permissions, "菜单查询", "system:menu:query", PermissionTypeConst.API, "菜单查询权限");
//        addPermissionIfNotExist(permissions, "菜单创建", "system:menu:create", PermissionTypeConst.API, "菜单创建权限");
//        addPermissionIfNotExist(permissions, "菜单修改", "system:menu:update", PermissionTypeConst.API, "菜单修改权限");
//        addPermissionIfNotExist(permissions, "菜单删除", "system:menu:delete", PermissionTypeConst.API, "菜单删除权限");
//
//        // 权限管理权限
//        addPermissionIfNotExist(permissions, "权限查询", "system:permission:query", PermissionTypeConst.API, "权限查询权限");
//        addPermissionIfNotExist(permissions, "权限创建", "system:permission:create", PermissionTypeConst.API, "权限创建权限");
//        addPermissionIfNotExist(permissions, "权限修改", "system:permission:update", PermissionTypeConst.API, "权限修改权限");
//        addPermissionIfNotExist(permissions, "权限删除", "system:permission:delete", PermissionTypeConst.API, "权限删除权限");
//
//        if (!permissions.isEmpty()) {
//            systemPermissionService.saveBatch(permissions);
//            log.info("成功初始化 {} 个基本权限", permissions.size());
//        } else {
//            log.info("基本权限已存在，无需初始化");
//        }
//    }
//
//    /**
//     * 添加权限（如果不存在）
//     */
//    private void addPermissionIfNotExist(List<SystemPermissionEntity> permissions, String name, String code, String type, String description) {
//        LambdaQueryWrapper<SystemPermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemPermissionEntity::getCode, code);
//        if (systemPermissionService.count(queryWrapper) == 0) {
//            SystemPermissionEntity permission = new SystemPermissionEntity();
//            permission.setName(name);
//            permission.setCode(code);
//            permission.setType(type);
//            permission.setDescription(description);
//            permission.setStatus(1);
//            permissions.add(permission);
//        }
//    }
//}