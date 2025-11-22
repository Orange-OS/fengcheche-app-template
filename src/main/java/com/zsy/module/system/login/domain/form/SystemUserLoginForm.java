package com.zsy.module.system.login.domain.form;

import com.zsy.module.system.captcha.domain.form.CaptchaForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录表单
 */
@Data
public class SystemUserLoginForm extends CaptchaForm {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
