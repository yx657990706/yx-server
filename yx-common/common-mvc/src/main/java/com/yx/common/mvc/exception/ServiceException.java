package com.yx.common.mvc.exception;

import com.yx.common.base.enums.EnumResponseCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

	/**
	 * 代码
	 */
	private Integer code;
	/**
	 * 消息
	 */
	private String msg;


	/**
	 * 构造方法一
	 * 
	 * @param code
	 * @param msg
	 */
	public ServiceException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}
	/**
	 * 构造方法二(传入枚举)
	 * @param resltEnum
	 */
	public ServiceException(EnumResponseCode resltEnum) {
		super(resltEnum.getMsg());
		this.code = resltEnum.getCode();
	}

}
