package com.zsy.module.system.auth.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zsy.common.constant.SaSessionConst;
import com.zsy.common.domain.Result;
import com.zsy.framework.common.exception.BusinessException;
import com.zsy.module.system.auth.domain.form.SystemUserLoginForm;
import com.zsy.module.system.auth.domain.vo.LoginResultVO;
import com.zsy.module.system.captcha.service.CaptchaService;
import com.zsy.module.system.menu.domain.entity.SystemMenuEntity;
import com.zsy.module.system.menu.service.SystemMenuService;
import com.zsy.module.system.permission.service.SystemPermissionService;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录服务实现类
 */
@Service
public class SystemAuthServiceImpl implements SystemAuthService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemMenuService systemMenuService;

    @Resource
    private SystemPermissionService systemPermissionService;

    @Resource
    private CaptchaService captchaService;

    @Override
    public Result<LoginResultVO> login(SystemUserLoginForm systemUserLoginForm) {

        // 校验 图形验证码
        Result<String> checkCaptcha = captchaService.checkCaptcha(systemUserLoginForm);

        if (!checkCaptcha.isSuccess()) {
            return Result.warning(checkCaptcha.getMsg());
        }

        String username = systemUserLoginForm.getUsername();
        String password = systemUserLoginForm.getPassword();

        // 查询用户
        SystemUserEntity user = systemUserService.selectByUsername(username);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        // 密码校验
        String encryptPassword = SaSecureUtil.md5(password);
        if (!user.getPassword().equals(encryptPassword)) {
            throw BusinessException.of("密码错误");
        }

        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw BusinessException.of("用户已被禁用");
        }

        // 更新登录信息
        user.setLastLoginTime(LocalDateTime.now());
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 设置登录 IP
        user.setLoginIp(ServletUtil.getClientIP(request));
        systemUserService.updateById(user);

        // 登录
        StpUtil.login(user.getId());

        // 获取当前会话的 Account-Session
        SaSession session = StpUtil.getSession();
        session.set(SaSessionConst.USER_ID, user.getId());
        // Sa-Token Session 底层使用 ConcurrentHashMap，不允许写入 null 值。
        setSessionValueIfNotNull(session, SaSessionConst.REGION_CODE, user.getRegionCode());
        setSessionValueIfNotNull(session, SaSessionConst.DEPT_ID, user.getDeptId());

        // 返回 token 和用户信息
        LoginResultVO loginResultVO = new LoginResultVO();
        loginResultVO.setToken(StpUtil.getTokenValue());

        // 清除敏感信息
        user.setPassword("***");
        loginResultVO.setSystemUserEntity(user);

        return Result.success(loginResultVO);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public SystemUserEntity getUserInfo() {
        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        SystemUserEntity user = systemUserService.selectById(Long.parseLong(loginId.toString()));

        // 清除敏感信息
        if (user != null) {
            user.setPassword(null);
        }

        return user;
    }

    @Override
    public List<SystemMenuEntity> getUserMenuList() {

        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        if (StpUtil.hasRole("admin")) {
            userId = null;
        }

        // 获取用户菜单树
        return systemMenuService.getMenuTreeByUserId(userId);
    }

    @Override
    public List<SystemMenuEntity> getUserPermissionList() {
        // 获取当前登录用户ID
        Object loginId = StpUtil.getLoginId();
        Long userId = Long.parseLong(loginId.toString());

        // 获取用户权限列表
        return systemPermissionService.getPermissionsByUserId(userId);
    }

    public static void main(String[] args) {
        System.out.println(SaSecureUtil.md5("123456"));
    }

    private void setSessionValueIfNotNull(SaSession session, String key, Object value) {
        if (value != null) {
            session.set(key, value);
        }
    }
} 
