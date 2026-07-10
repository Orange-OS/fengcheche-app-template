package com.zsy.common.enums;

/**
 * 状态枚举
 */
public enum StatusEnum {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private final Integer code;
    private final String description;

    StatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code 状态代码
     * @return 对应的枚举，未找到时返回 null
     */
    public static StatusEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StatusEnum status : StatusEnum.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断是否为启用状态
     */
    public boolean isEnabled() {
        return this == ENABLED;
    }

    /**
     * 判断是否为禁用状态
     */
    public boolean isDisabled() {
        return this == DISABLED;
    }
}
