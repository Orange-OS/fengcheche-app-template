//package com.zsy.config;
//
//import cn.dev33.satoken.secure.SaSecureUtil;
//import com.zsy.constant.PermissionTypeConst;
//import com.zsy.module.system.menu.domain.SystemMenuEntity;
//import com.zsy.module.system.menu.service.SystemMenuService;
//import com.zsy.module.system.permission.domain.SystemPermissionEntity;
//import com.zsy.module.system.permission.service.SystemPermissionService;
//import com.zsy.module.system.role.domain.SystemRoleEntity;
//import com.zsy.module.system.role.service.SystemRoleService;
//import com.zsy.module.system.rolemenu.domain.SystemRoleMenuEntity;
//import com.zsy.module.system.rolemenu.service.SystemRoleMenuService;
//import com.zsy.module.system.rolepermission.domain.SystemRolePermissionEntity;
//import com.zsy.module.system.rolepermission.service.SystemRolePermissionService;
//import com.zsy.module.system.user.domain.SystemUserEntity;
//import com.zsy.module.system.user.service.SystemUserService;
//import com.zsy.module.system.userrole.domain.SystemUserRoleEntity;
//import com.zsy.module.system.userrole.service.SystemUserRoleService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 系统数据初始化
// */
//@Slf4j
//@Component
//@Order(1)
//public class DataInitializer implements CommandLineRunner {
//
//    @Resource
//    private SystemUserService systemUserService;
//
//    @Resource
//    private SystemRoleService systemRoleService;
//
//    @Resource
//    private SystemMenuService systemMenuService;
//
//    @Resource
//    private SystemPermissionService systemPermissionService;
//
//    @Resource
//    private SystemUserRoleService systemUserRoleService;
//
//    @Resource
//    private SystemRoleMenuService systemRoleMenuService;
//
//    @Resource
//    private SystemRolePermissionService systemRolePermissionService;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void run(String... args) {
//        // 初始化角色
//        initRoles();
//        // 初始化用户
//        initUsers();
//        // 初始化菜单
//        initMenus();
//        // 初始化权限
//        initPermissions();
//        // 初始化角色-菜单关联
//        initRoleMenus();
//        // 初始化角色-权限关联
//        initRolePermissions();
//        // 初始化用户-角色关联
//        initUserRoles();
//    }
//
//    /**
//     * 初始化角色
//     */
//    private void initRoles() {
//        log.info("开始初始化角色...");
//
//        // 检查是否已存在角色
//        if (systemRoleService.count() > 0) {
//            log.info("角色已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemRoleEntity> roles = new ArrayList<>();
//
//        // 管理员角色
//        SystemRoleEntity adminRole = new SystemRoleEntity();
//        adminRole.setName("管理员");
//        adminRole.setCode("admin");
//        adminRole.setDescription("系统管理员，拥有所有权限");
//        adminRole.setStatus(1);
//        adminRole.setSort(1);
//        roles.add(adminRole);
//
//        // 普通用户角色
//        SystemRoleEntity userRole = new SystemRoleEntity();
//        userRole.setName("普通用户");
//        userRole.setCode("user");
//        userRole.setDescription("普通用户，拥有基本权限");
//        userRole.setStatus(1);
//        userRole.setSort(2);
//        roles.add(userRole);
//
//        // 游客角色
//        SystemRoleEntity guestRole = new SystemRoleEntity();
//        guestRole.setName("游客");
//        guestRole.setCode("guest");
//        guestRole.setDescription("游客，只有查看权限");
//        guestRole.setStatus(1);
//        guestRole.setSort(3);
//        roles.add(guestRole);
//
//        systemRoleService.saveBatch(roles);
//        log.info("角色初始化完成，共 {} 个角色", roles.size());
//    }
//
//    /**
//     * 初始化用户
//     */
//    private void initUsers() {
//        log.info("开始初始化用户...");
//
//        // 检查是否已存在用户
//        if (systemUserService.count() > 0) {
//            log.info("用户已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemUserEntity> users = new ArrayList<>();
//
//        // 管理员用户
//        SystemUserEntity adminUser = new SystemUserEntity();
//        adminUser.setUsername("admin");
//        adminUser.setPassword(SaSecureUtil.md5("123456"));
//        adminUser.setNickname("系统管理员");
//        adminUser.setEmail("admin@example.com");
//        adminUser.setMobile("13800138000");
//        adminUser.setSex(1);
//        adminUser.setStatus(1);
//        adminUser.setRemark("系统管理员账号");
//        users.add(adminUser);
//
//        // 普通用户
//        SystemUserEntity normalUser = new SystemUserEntity();
//        normalUser.setUsername("user");
//        normalUser.setPassword(SaSecureUtil.md5("123456"));
//        normalUser.setNickname("普通用户");
//        normalUser.setEmail("user@example.com");
//        normalUser.setMobile("13800138001");
//        normalUser.setSex(1);
//        normalUser.setStatus(1);
//        normalUser.setRemark("普通用户账号");
//        users.add(normalUser);
//
//        // 游客用户
//        SystemUserEntity guestUser = new SystemUserEntity();
//        guestUser.setUsername("guest");
//        guestUser.setPassword(SaSecureUtil.md5("123456"));
//        guestUser.setNickname("游客");
//        guestUser.setEmail("guest@example.com");
//        guestUser.setMobile("13800138002");
//        guestUser.setSex(0);
//        guestUser.setStatus(1);
//        guestUser.setRemark("游客账号");
//        users.add(guestUser);
//
//        systemUserService.saveBatch(users);
//        log.info("用户初始化完成，共 {} 个用户", users.size());
//    }
//
//    /**
//     * 初始化菜单
//     */
//    private void initMenus() {
//        log.info("开始初始化菜单...");
//
//        // 检查是否已存在菜单
//        if (systemMenuService.count() > 0) {
//            log.info("菜单已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemMenuEntity> menus = new ArrayList<>();
//
//        // 系统管理
//        SystemMenuEntity systemMenu = new SystemMenuEntity();
//        systemMenu.setName("系统管理");
//        systemMenu.setParentId(0L);
//        systemMenu.setOrderNum(1);
//        systemMenu.setPath("/system");
//        systemMenu.setComponent("Layout");
//        systemMenu.setIsFrame(0);
//        systemMenu.setIsCache(0);
//        systemMenu.setMenuType("M");
//        systemMenu.setVisible(1);
//        systemMenu.setStatus(1);
//        systemMenu.setPermission("");
//        systemMenu.setIcon("system");
//        systemMenu.setCreateBy("admin");
//        menus.add(systemMenu);
//
//        // 用户管理
//        SystemMenuEntity userMenu = new SystemMenuEntity();
//        userMenu.setName("用户管理");
//        userMenu.setParentId(1L); // 系统管理的ID为1
//        userMenu.setOrderNum(1);
//        userMenu.setPath("user");
//        userMenu.setComponent("system/user/index");
//        userMenu.setIsFrame(0);
//        userMenu.setIsCache(0);
//        userMenu.setMenuType("C");
//        userMenu.setVisible(1);
//        userMenu.setStatus(1);
//        userMenu.setPermission("system:user:list");
//        userMenu.setIcon("user");
//        userMenu.setCreateBy("admin");
//        menus.add(userMenu);
//
//        // 角色管理
//        SystemMenuEntity roleMenu = new SystemMenuEntity();
//        roleMenu.setName("角色管理");
//        roleMenu.setParentId(1L);
//        roleMenu.setOrderNum(2);
//        roleMenu.setPath("role");
//        roleMenu.setComponent("system/role/index");
//        roleMenu.setIsFrame(0);
//        roleMenu.setIsCache(0);
//        roleMenu.setMenuType("C");
//        roleMenu.setVisible(1);
//        roleMenu.setStatus(1);
//        roleMenu.setPermission("system:role:list");
//        roleMenu.setIcon("peoples");
//        roleMenu.setCreateBy("admin");
//        menus.add(roleMenu);
//
//        // 菜单管理
//        SystemMenuEntity menuMenu = new SystemMenuEntity();
//        menuMenu.setName("菜单管理");
//        menuMenu.setParentId(1L);
//        menuMenu.setOrderNum(3);
//        menuMenu.setPath("menu");
//        menuMenu.setComponent("system/menu/index");
//        menuMenu.setIsFrame(0);
//        menuMenu.setIsCache(0);
//        menuMenu.setMenuType("C");
//        menuMenu.setVisible(1);
//        menuMenu.setStatus(1);
//        menuMenu.setPermission("system:menu:list");
//        menuMenu.setIcon("tree-table");
//        menuMenu.setCreateBy("admin");
//        menus.add(menuMenu);
//
//        // 权限管理
//        SystemMenuEntity permissionMenu = new SystemMenuEntity();
//        permissionMenu.setName("权限管理");
//        permissionMenu.setParentId(1L);
//        permissionMenu.setOrderNum(4);
//        permissionMenu.setPath("permission");
//        permissionMenu.setComponent("system/permission/index");
//        permissionMenu.setIsFrame(0);
//        permissionMenu.setIsCache(0);
//        permissionMenu.setMenuType("C");
//        permissionMenu.setVisible(1);
//        permissionMenu.setStatus(1);
//        permissionMenu.setPermission("system:permission:list");
//        permissionMenu.setIcon("lock");
//        permissionMenu.setCreateBy("admin");
//        menus.add(permissionMenu);
//
//        // 用户管理按钮
//        addButtonMenu(menus, "用户查询", 2L, 1, "system:user:query", "search");
//        addButtonMenu(menus, "用户新增", 2L, 2, "system:user:create", "add");
//        addButtonMenu(menus, "用户修改", 2L, 3, "system:user:update", "edit");
//        addButtonMenu(menus, "用户删除", 2L, 4, "system:user:delete", "delete");
//
//        // 角色管理按钮
//        addButtonMenu(menus, "角色查询", 3L, 1, "system:role:query", "search");
//        addButtonMenu(menus, "角色新增", 3L, 2, "system:role:create", "add");
//        addButtonMenu(menus, "角色修改", 3L, 3, "system:role:update", "edit");
//        addButtonMenu(menus, "角色删除", 3L, 4, "system:role:delete", "delete");
//
//        // 菜单管理按钮
//        addButtonMenu(menus, "菜单查询", 4L, 1, "system:menu:query", "search");
//        addButtonMenu(menus, "菜单新增", 4L, 2, "system:menu:create", "add");
//        addButtonMenu(menus, "菜单修改", 4L, 3, "system:menu:update", "edit");
//        addButtonMenu(menus, "菜单删除", 4L, 4, "system:menu:delete", "delete");
//
//        // 权限管理按钮
//        addButtonMenu(menus, "权限查询", 5L, 1, "system:permission:query", "search");
//        addButtonMenu(menus, "权限新增", 5L, 2, "system:permission:create", "add");
//        addButtonMenu(menus, "权限修改", 5L, 3, "system:permission:update", "edit");
//        addButtonMenu(menus, "权限删除", 5L, 4, "system:permission:delete", "delete");
//
//        systemMenuService.saveBatch(menus);
//        log.info("菜单初始化完成，共 {} 个菜单", menus.size());
//    }
//
//    /**
//     * 添加按钮菜单
//     */
//    private void addButtonMenu(List<SystemMenuEntity> menus, String name, Long parentId, int orderNum, String permission, String icon) {
//        SystemMenuEntity buttonMenu = new SystemMenuEntity();
//        buttonMenu.setName(name);
//        buttonMenu.setParentId(parentId);
//        buttonMenu.setOrderNum(orderNum);
//        buttonMenu.setPath("");
//        buttonMenu.setComponent("");
//        buttonMenu.setIsFrame(0);
//        buttonMenu.setIsCache(0);
//        buttonMenu.setMenuType("F");
//        buttonMenu.setVisible(1);
//        buttonMenu.setStatus(1);
//        buttonMenu.setPermission(permission);
//        buttonMenu.setIcon(icon);
//        buttonMenu.setCreateBy("admin");
//        menus.add(buttonMenu);
//    }
//
//    /**
//     * 初始化权限
//     */
//    private void initPermissions() {
//        log.info("开始初始化权限...");
//
//        // 检查是否已存在权限
//        if (systemPermissionService.count() > 0) {
//            log.info("权限已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemPermissionEntity> permissions = new ArrayList<>();
//
//        // 用户管理权限
//        addPermission(permissions, "用户查询", "system:user:query", PermissionTypeConst.API, "用户查询权限", 2L);
//        addPermission(permissions, "用户创建", "system:user:create", PermissionTypeConst.API, "用户创建权限", 2L);
//        addPermission(permissions, "用户修改", "system:user:update", PermissionTypeConst.API, "用户修改权限", 2L);
//        addPermission(permissions, "用户删除", "system:user:delete", PermissionTypeConst.API, "用户删除权限", 2L);
//
//        // 角色管理权限
//        addPermission(permissions, "角色查询", "system:role:query", PermissionTypeConst.API, "角色查询权限", 3L);
//        addPermission(permissions, "角色创建", "system:role:create", PermissionTypeConst.API, "角色创建权限", 3L);
//        addPermission(permissions, "角色修改", "system:role:update", PermissionTypeConst.API, "角色修改权限", 3L);
//        addPermission(permissions, "角色删除", "system:role:delete", PermissionTypeConst.API, "角色删除权限", 3L);
//
//        // 菜单管理权限
//        addPermission(permissions, "菜单查询", "system:menu:query", PermissionTypeConst.API, "菜单查询权限", 4L);
//        addPermission(permissions, "菜单创建", "system:menu:create", PermissionTypeConst.API, "菜单创建权限", 4L);
//        addPermission(permissions, "菜单修改", "system:menu:update", PermissionTypeConst.API, "菜单修改权限", 4L);
//        addPermission(permissions, "菜单删除", "system:menu:delete", PermissionTypeConst.API, "菜单删除权限", 4L);
//
//        // 权限管理权限
//        addPermission(permissions, "权限查询", "system:permission:query", PermissionTypeConst.API, "权限查询权限", 5L);
//        addPermission(permissions, "权限创建", "system:permission:create", PermissionTypeConst.API, "权限创建权限", 5L);
//        addPermission(permissions, "权限修改", "system:permission:update", PermissionTypeConst.API, "权限修改权限", 5L);
//        addPermission(permissions, "权限删除", "system:permission:delete", PermissionTypeConst.API, "权限删除权限", 5L);
//
//        systemPermissionService.saveBatch(permissions);
//        log.info("权限初始化完成，共 {} 个权限", permissions.size());
//    }
//
//    /**
//     * 添加权限
//     */
//    private void addPermission(List<SystemPermissionEntity> permissions, String name, String code, String type, String description, Long menuId) {
//        SystemPermissionEntity permission = new SystemPermissionEntity();
//        permission.setName(name);
//        permission.setCode(code);
//        permission.setType(type);
//        permission.setDescription(description);
//        permission.setMenuId(menuId);
//        permission.setStatus(1);
//        permission.setCreateBy("admin");
//        permissions.add(permission);
//    }
//
//    /**
//     * 初始化角色-菜单关联
//     */
//    private void initRoleMenus() {
//        log.info("开始初始化角色-菜单关联...");
//
//        // 检查是否已存在角色-菜单关联
//        if (systemRoleMenuService.count() > 0) {
//            log.info("角色-菜单关联已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemRoleMenuEntity> roleMenus = new ArrayList<>();
//
//        // 获取角色
//        List<SystemRoleEntity> roles = systemRoleService.list();
//        if (roles.isEmpty()) {
//            log.warn("未找到角色，无法初始化角色-菜单关联");
//            return;
//        }
//
//        // 获取菜单
//        List<SystemMenuEntity> menus = systemMenuService.list();
//        if (menus.isEmpty()) {
//            log.warn("未找到菜单，无法初始化角色-菜单关联");
//            return;
//        }
//
//        // 管理员角色拥有所有菜单权限
//        Long adminRoleId = roles.stream()
//                .filter(role -> "admin".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (adminRoleId != null) {
//            for (SystemMenuEntity menu : menus) {
//                SystemRoleMenuEntity roleMenu = new SystemRoleMenuEntity();
//                roleMenu.setRoleId(adminRoleId);
//                roleMenu.setMenuId(menu.getId());
//                roleMenus.add(roleMenu);
//            }
//        }
//
//        // 普通用户角色拥有部分菜单权限（用户管理的查询权限）
//        Long userRoleId = roles.stream()
//                .filter(role -> "user".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (userRoleId != null) {
//            // 系统管理
//            menus.stream()
//                    .filter(menu -> "系统管理".equals(menu.getName()))
//                    .findFirst()
//                    .ifPresent(menu -> {
//                        SystemRoleMenuEntity roleMenu = new SystemRoleMenuEntity();
//                        roleMenu.setRoleId(userRoleId);
//                        roleMenu.setMenuId(menu.getId());
//                        roleMenus.add(roleMenu);
//                    });
//
//            // 用户管理
//            menus.stream()
//                    .filter(menu -> "用户管理".equals(menu.getName()))
//                    .findFirst()
//                    .ifPresent(menu -> {
//                        SystemRoleMenuEntity roleMenu = new SystemRoleMenuEntity();
//                        roleMenu.setRoleId(userRoleId);
//                        roleMenu.setMenuId(menu.getId());
//                        roleMenus.add(roleMenu);
//                    });
//
//            // 用户查询按钮
//            menus.stream()
//                    .filter(menu -> "用户查询".equals(menu.getName()))
//                    .findFirst()
//                    .ifPresent(menu -> {
//                        SystemRoleMenuEntity roleMenu = new SystemRoleMenuEntity();
//                        roleMenu.setRoleId(userRoleId);
//                        roleMenu.setMenuId(menu.getId());
//                        roleMenus.add(roleMenu);
//                    });
//        }
//
//        systemRoleMenuService.saveBatch(roleMenus);
//        log.info("角色-菜单关联初始化完成，共 {} 条记录", roleMenus.size());
//    }
//
//    /**
//     * 初始化角色-权限关联
//     */
//    private void initRolePermissions() {
//        log.info("开始初始化角色-权限关联...");
//
//        // 检查是否已存在角色-权限关联
//        if (systemRolePermissionService.count() > 0) {
//            log.info("角色-权限关联已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemRolePermissionEntity> rolePermissions = new ArrayList<>();
//
//        // 获取角色
//        List<SystemRoleEntity> roles = systemRoleService.list();
//        if (roles.isEmpty()) {
//            log.warn("未找到角色，无法初始化角色-权限关联");
//            return;
//        }
//
//        // 获取权限
//        List<SystemPermissionEntity> permissions = systemPermissionService.list();
//        if (permissions.isEmpty()) {
//            log.warn("未找到权限，无法初始化角色-权限关联");
//            return;
//        }
//
//        // 管理员角色拥有所有权限
//        Long adminRoleId = roles.stream()
//                .filter(role -> "admin".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (adminRoleId != null) {
//            for (SystemPermissionEntity permission : permissions) {
//                SystemRolePermissionEntity rolePermission = new SystemRolePermissionEntity();
//                rolePermission.setRoleId(adminRoleId);
//                rolePermission.setPermissionId(permission.getId());
//                rolePermissions.add(rolePermission);
//            }
//        }
//
//        // 普通用户角色拥有部分权限（用户查询权限）
//        Long userRoleId = roles.stream()
//                .filter(role -> "user".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (userRoleId != null) {
//            permissions.stream()
//                    .filter(permission -> "system:user:query".equals(permission.getCode()))
//                    .findFirst()
//                    .ifPresent(permission -> {
//                        SystemRolePermissionEntity rolePermission = new SystemRolePermissionEntity();
//                        rolePermission.setRoleId(userRoleId);
//                        rolePermission.setPermissionId(permission.getId());
//                        rolePermissions.add(rolePermission);
//                    });
//        }
//
//        systemRolePermissionService.saveBatch(rolePermissions);
//        log.info("角色-权限关联初始化完成，共 {} 条记录", rolePermissions.size());
//    }
//
//    /**
//     * 初始化用户-角色关联
//     */
//    private void initUserRoles() {
//        log.info("开始初始化用户-角色关联...");
//
//        // 检查是否已存在用户-角色关联
//        if (systemUserRoleService.count() > 0) {
//            log.info("用户-角色关联已存在，跳过初始化");
//            return;
//        }
//
//        List<SystemUserRoleEntity> userRoles = new ArrayList<>();
//
//        // 获取用户
//        List<SystemUserEntity> users = systemUserService.list();
//        if (users.isEmpty()) {
//            log.warn("未找到用户，无法初始化用户-角色关联");
//            return;
//        }
//
//        // 获取角色
//        List<SystemRoleEntity> roles = systemRoleService.list();
//        if (roles.isEmpty()) {
//            log.warn("未找到角色，无法初始化用户-角色关联");
//            return;
//        }
//
//        // admin用户关联管理员角色
//        Long adminUserId = users.stream()
//                .filter(user -> "admin".equals(user.getUsername()))
//                .findFirst()
//                .map(SystemUserEntity::getId)
//                .orElse(null);
//
//        Long adminRoleId = roles.stream()
//                .filter(role -> "admin".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (adminUserId != null && adminRoleId != null) {
//            SystemUserRoleEntity userRole = new SystemUserRoleEntity();
//            userRole.setUserId(adminUserId);
//            userRole.setRoleId(adminRoleId);
//            userRoles.add(userRole);
//        }
//
//        // user用户关联普通用户角色
//        Long normalUserId = users.stream()
//                .filter(user -> "user".equals(user.getUsername()))
//                .findFirst()
//                .map(SystemUserEntity::getId)
//                .orElse(null);
//
//        Long userRoleId = roles.stream()
//                .filter(role -> "user".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (normalUserId != null && userRoleId != null) {
//            SystemUserRoleEntity userRole = new SystemUserRoleEntity();
//            userRole.setUserId(normalUserId);
//            userRole.setRoleId(userRoleId);
//            userRoles.add(userRole);
//        }
//
//        // guest用户关联游客角色
//        Long guestUserId = users.stream()
//                .filter(user -> "guest".equals(user.getUsername()))
//                .findFirst()
//                .map(SystemUserEntity::getId)
//                .orElse(null);
//
//        Long guestRoleId = roles.stream()
//                .filter(role -> "guest".equals(role.getCode()))
//                .findFirst()
//                .map(SystemRoleEntity::getId)
//                .orElse(null);
//
//        if (guestUserId != null && guestRoleId != null) {
//            SystemUserRoleEntity userRole = new SystemUserRoleEntity();
//            userRole.setUserId(guestUserId);
//            userRole.setRoleId(guestRoleId);
//            userRoles.add(userRole);
//        }
//
//        systemUserRoleService.saveBatch(userRoles);
//        log.info("用户-角色关联初始化完成，共 {} 条记录", userRoles.size());
//    }
//}