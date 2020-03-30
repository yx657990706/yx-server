package com.yx.common.rabittmq.modle;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:08 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p> 自定义队列传输对象
 * --------------------------------------------------
 * </pre>
 */
public class QueueMessge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     */
    private HashMap<String, Object> msg = new HashMap<String, Object>();

    /**
     * beanName 可以通过getBean的方式获取处理类
     */
    public void setBeanName(String beanName) {
        this.msg.put("beanName", beanName);
    }

    /**
     * jsonData json格式的字符串数据
     */
    public void setJsonData(String jsonData) {
        this.msg.put("jsonData", jsonData);
    }

    public HashMap<String, Object> getMsg() {
        return msg;
    }

    public void setContent(HashMap<String, Object> content) {
        this.msg = content;
    }

    public void put(String key, Object obj) {
        this.getMsg().put(key, obj);
    }

    public Object get(String key) {
        return this.getMsg().get(key);
    }

    public String toJsonString() {
        return JSON.toJSONString(msg);
    }
}
