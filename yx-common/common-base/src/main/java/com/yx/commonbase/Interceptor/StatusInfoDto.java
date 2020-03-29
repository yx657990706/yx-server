package com.yx.commonbase.Interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Jesse
 * @Date 2019/6/25 16:46
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusInfoDto implements Serializable {

    private static final long serialVersionUID = -3003847539475261253L;

    /**
     * 大类  1：HTTP请求类   2：log埋点类  其他重新定义
     */
    private Integer event;
    /**
     * 细类  http包含okhttp/httpclient ；log包含log 其他重新定义
     */
    private String type;
    /**
     * 请求的url
     */
    private String url;
    /**
     * 请求的方式（get/post）
     */
    private String method;
    /**
     * 状态码
     *  <br>HTTP的状态码描述  2**：成功     3** ：重定向错误   4** :客户端错误 5**：服务器错误
     *  <br>自定义状态码  9**：自定义错误  参见MyHttpStatusEnum类的描述
     */
    private Integer status;
    /**
     * 状态描述
     */
    private String msg;
    /**
     * 耗时(单位：毫秒)
     */
    private Long spendTime;

    /**
     * 异常描述  (e.getMessage)
     */
    private String errorInfo;

    /**
     *   行为
     *  参见GlActionEnum
     */
    private String action;
    /**
     *  渠道ID
     */
    private String channelId;
    /**
     * 渠道名称
     */
    private String channelName;
    /**
     *  用户id
     */
    private Integer userId ;
    /**
     * 用户名
     */
    private String userName ;
    /**
     * 转账单号
     */
    private String tradeId ;
    /**
     *  终端类型：  PC、H5、安卓、IOS、PAD
     */
    private String terminal;
}
