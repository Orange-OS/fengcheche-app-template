package com.zsy.module.system.captcha.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CaptchaForm {

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "验证码uuid标识不能为空")
    private String captchaUuid;
}
