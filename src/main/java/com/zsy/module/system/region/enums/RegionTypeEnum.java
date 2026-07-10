package com.zsy.module.system.region.enums;

/**
 * 地域类型枚举
 */
public enum RegionTypeEnum {
    PROVINCE(1, "省"),
    CITY(2, "市"),
    DISTRICT(3, "区县"),
    TOWN(4, "乡镇"),
    VILLAGE(5, "村/社区");

    private final Integer code;
    private final String description;

    RegionTypeEnum(Integer code, String description) {
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
     * @param code 地域类型代码
     * @return 对应的枚举，未找到时返回 null
     */
    public static RegionTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RegionTypeEnum type : RegionTypeEnum.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断是否为省级
     */
    public boolean isProvince() {
        return this == PROVINCE;
    }

    /**
     * 判断是否为市级
     */
    public boolean isCity() {
        return this == CITY;
    }

    /**
     * 判断是否为区县级
     */
    public boolean isDistrict() {
        return this == DISTRICT;
    }

    /**
     * 判断是否为乡镇级
     */
    public boolean isTown() {
        return this == TOWN;
    }

    /**
     * 判断是否为村/社区级
     */
    public boolean isVillage() {
        return this == VILLAGE;
    }
}
