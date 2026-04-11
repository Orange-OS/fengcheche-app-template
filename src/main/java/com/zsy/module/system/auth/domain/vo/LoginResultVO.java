package com.zsy.module.system.auth.domain.vo;

import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录结果视图对象
 */
@Data
public class LoginResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户令牌
     */
    private String token;

    /**
     * 用户信息
     */
    private SystemUserEntity systemUserEntity;
}
