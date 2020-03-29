package com.yx.commonredis.enums;

public enum EnumRegion {

    /**
     * 锁
     */
    MY_LOCK,
    /**
     * 验证码缓存域
     */
    CAPTCHA_REGION,
    /**
     * 微信缓存域
     */
    WECHAT_REGION,
    /**
     * 普通缓存域
     */
     NOMORL_REGION,
    /**
     * 特殊缓存域
     */
    SP_REGION;
    /**
     * 根据名字返回Region
     * @param regionName
     * @return
     */
    public static EnumRegion getRegionByName(String regionName) {
        EnumRegion regionEnum = null;
        for (EnumRegion r : EnumRegion.values()) {
            if (r.name().equals(regionName)) {
                regionEnum = r;
                break;
            }
        }
        return regionEnum;
    }
}
