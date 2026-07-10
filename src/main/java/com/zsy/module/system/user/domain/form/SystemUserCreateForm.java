package com.zsy.module.system.user.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SystemUserCreateForm {

    /**
     * 用户账号
     */
    @NotEmpty(message = "用户账号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 所属地域
     */
    @Schema(name = "所属地域")
    private String regionCode;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 帐号状态（1-正常 0-停用）
     */
    private Integer status;
}
