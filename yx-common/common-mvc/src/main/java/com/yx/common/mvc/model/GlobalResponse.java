package com.yx.common.mvc.model;

import io.swagger.annotations.ApiModelProperty;

public class GlobalResponse<T> {

	/**
	 * 结果码
	 */
	@ApiModelProperty(value = "结果码")
	private String code;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String msg;
	/**
	 * 返回数据
	 */
	@ApiModelProperty(value = "返回对象")
	private T data;

    public GlobalResponse(){

	}
	public GlobalResponse(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	public GlobalResponse(String code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
