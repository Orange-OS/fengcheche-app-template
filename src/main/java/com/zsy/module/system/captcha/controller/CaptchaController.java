package com.zsy.module.system.captcha.controller;

import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.captcha.domain.vo.CaptchaVO;
import com.zsy.module.system.captcha.service.CaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system-captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/generate")
    public Result<CaptchaVO> generate() {
        return Result.success(captchaService.generateCaptcha());
    }

}
