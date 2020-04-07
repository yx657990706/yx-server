package com.yx.common.mvc.utils;


import com.yx.common.base.enums.EnumResponseCode;
import com.yx.common.base.restful.GlobalResponse;

/**
 * 统一消息返回工具类
 *
 * @author jesse
 */
public class ResponseUtil {

    /**
     * 成功(有返回数据)
     *
     * @param object
     * @return
     */
    public static GlobalResponse success(final Object object) {
        if (object == null) {
            return new GlobalResponse(EnumResponseCode.SUCCESS_CODE.getCode(),EnumResponseCode.SUCCESS_CODE.getMsg());
        }
        return new GlobalResponse(EnumResponseCode.SUCCESS_CODE.getCode(),EnumResponseCode.SUCCESS_CODE.getMsg(), object);
    }

    /**
     * 成功(无返回数据)
     *
     * @return
     */
    public static GlobalResponse success() {
        return success(null);
    }

    /**
     * 失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static GlobalResponse error(Integer code, String msg) {
        return new GlobalResponse(code, msg);
    }

    /**
     * 失败（使用枚举传入）
     * @param enumResponseCode
     * @return
     */
    public static GlobalResponse error(EnumResponseCode enumResponseCode) {
        return new GlobalResponse(enumResponseCode.getCode(), enumResponseCode.getMsg());
    }
}
