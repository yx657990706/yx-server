package com.yx.commonbase.enums;


public enum EnumResponseCode {
    /**
     * 正确
     **/
    SUCCESS_CODE(200, "成功"),
    PARAM_ERROR_CODE(400, "参数错误"),
    LIMIT_ERROR_CODE(401, "限制调用"),
    TOKEN_TIMEOUT_CODE(402, "token 过期"),
    NO_AUTH_CODE(403, "禁止访问"),
    NOT_FOUND(404, "资源没找到"),
    DOWNGRADE(406, "服务降级中"),
    SERVER_ERROR_CODE(500, "服务器错误");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private EnumResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code获取描述
     *
     * @param code
     * @return
     */
    public static String getDescByCode(int code) {
        for (EnumResponseCode r : EnumResponseCode.values()) {
            if (r.getCode() == code) {
                return r.getMsg();
            }
        }
        return "";
    }
}
