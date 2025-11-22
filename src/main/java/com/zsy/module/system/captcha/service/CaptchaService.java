package com.zsy.module.system.captcha.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.RandomUtil;
import com.zsy.common.constant.StringConst;
import com.zsy.constant.RedisKeyConst;
import com.zsy.framework.common.pojo.Result;
import com.zsy.module.system.captcha.domain.form.CaptchaForm;
import com.zsy.module.system.captcha.domain.vo.CaptchaVO;
import com.zsy.module.system.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class CaptchaService {
    /**
     * 过期时间：65秒
     */
    private static final long EXPIRE_SECOND = 65L;
    @Resource
    private RedisService redisService;

    /**
     * 生成图形验证码
     * 默认 1 分钟有效期
     */
    public CaptchaVO generateCaptcha() {

        //生成四位验证码
        String captchaText = RandomUtil.randomNumbers(4);

        //定义图形验证码的长、宽、验证码位数、干扰线数量
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(125, 43, 4, 80);

        //设置背景颜色
        lineCaptcha.setBackground(new Color(230, 244, 255));

        //生成图片
        Image image = lineCaptcha.createImage(captchaText);

        //转为 base64
        String base64Code = ImgUtil.toBase64(image, "jpg");

        /*
         * 返回验证码对象
         * 图片base64格式
         */
        // UUID 唯一标识
        String uuid = UUID.randomUUID().toString().replace("-", StringConst.EMPTY);

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaUuid(uuid);
        captchaVO.setCaptchaBase64Image("data:image/png;base64," + base64Code);
        captchaVO.setExpireSeconds(EXPIRE_SECOND);
        String redisCaptchaKey = redisService.generateRedisKey(RedisKeyConst.Support.CAPTCHA, uuid);
        redisService.set(redisCaptchaKey, captchaText, EXPIRE_SECOND);
        return captchaVO;
    }

    /**
     * 校验图形验证码
     */
    public Result<String> checkCaptcha(CaptchaForm captchaForm) {
        if (StringUtils.isBlank(captchaForm.getCaptchaUuid()) || StringUtils.isBlank(captchaForm.getCaptcha())) {
            return Result.warning("请输入正确验证码");
        }

        /*
         * 1、校验redis里的验证码
         * 2、校验成功后，删除redis
         */
        String redisCaptchaKey = redisService.generateRedisKey(RedisKeyConst.Support.CAPTCHA, captchaForm.getCaptchaUuid());
        String redisCaptchaCode = redisService.get(redisCaptchaKey);
        if (StringUtils.isBlank(redisCaptchaCode)) {
            return Result.warning("验证码已过期，请刷新重试");
        }
        if (!Objects.equals(redisCaptchaCode, captchaForm.getCaptcha())) {
            return Result.warning("验证码错误，请输入正确的验证码");
        }
        // 删除已使用的验证码
        redisService.delete(redisCaptchaKey);
        return Result.success();
    }
}
