package com.zsy.module.system.captcha.domain.vo;

import lombok.Data;

/**
 * 图形验证码 VO
 */
@Data
public class CaptchaVO {
    /**
     * 验证码唯一标识
     */
    private String captchaUuid;

    /**
     * 验证码图片内容-生产环境无效
     */
    private String captchaText;
    /**
     * 验证码Base64图片
     */
    private String captchaBase64Image;
    /**
     * 过期时间（秒）
     */
    private Long expireSeconds;
}
