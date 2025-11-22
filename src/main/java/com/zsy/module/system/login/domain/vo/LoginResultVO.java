package com.zsy.module.system.login.domain.vo;

import com.zsy.module.system.user.domain.SystemUserEntity;
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
    private SystemUserEntity userInfo;
}
