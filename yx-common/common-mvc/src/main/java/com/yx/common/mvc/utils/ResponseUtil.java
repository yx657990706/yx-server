package com.yx.common.mvc.utils;


import com.yx.common.mvc.model.GlobalResponse;

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
            return new GlobalResponse("0000", "success");
        }
        return new GlobalResponse("0000", "success", object);
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
    public static GlobalResponse error(String code, String msg) {
        return new GlobalResponse(code, msg);
    }
}
