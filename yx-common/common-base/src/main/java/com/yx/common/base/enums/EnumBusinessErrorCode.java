package com.yx.common.base.enums;

import lombok.Getter;

@Getter
public enum EnumBusinessErrorCode {

    /**
     * 用户不存在
     */
    USER_NOT_FIND(1001,"用户不存在"),
    /**
     * 登录名或密码，请重新登录
     */
    LOGIN_NAME_OR_PASSWORD_ERROR(1002,"登录名或密码，请重新登录"),
    /**
     * 验证码已过期，请重新获取
     */
    CAPTCHA_INVALID_ERROR(1003,"验证码已过期，请重新获取"),
    /**
     * 验证码错误，请重新输入
     */
    CAPTCHA_ERROR(1004,"验证码错误，请重新输入"),
    /**
     * 产品不存在
     */
    GOODS_NOT_FIND(1005,"产品不存在"),
    /**
     * 库存不足
     */
    LOW_STOCK_ERROR(1006,"库存不足"),
    /**
     * 订单不存在
     */
    ORDER_NOT_FIND(1007,"订单不存在");


    EnumBusinessErrorCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 结果码
     */
    private Integer errorCode;
    /**
     * 代码描述
     */
    private String errorMsg;

    /**
     * 根据code获取描述
     * @param code
     * @return
     */
    public static String getDescByCode(Integer code) {
        for (EnumBusinessErrorCode r : EnumBusinessErrorCode.values()) {
            if (r.getErrorCode().equals(code)) {
                return r.getErrorMsg();
            }
        }
        return "";
    }
}
