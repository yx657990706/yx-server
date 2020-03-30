package com.yx.common.base.enums;

public enum MyHttpStatusEnum {

    HTTP_ERROR(900, "http请求异常"),
    HTTP_IO_ERROR(901, "IO异常"),
    HTTP_CONNECT_TIME_OUT(902, "连接超时"),
    HTTP_CONNECT_ERROR(903, "连接异常");

    /**
     * 状态码
     */
    private int status;
    /**
     * 描述
     */
    private String msg;

    private MyHttpStatusEnum(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
