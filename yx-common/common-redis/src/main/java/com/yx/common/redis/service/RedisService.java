package com.yx.common.redis.service;

import com.yx.common.redis.enums.EnumRegion;

import java.util.Set;

/**
 * @Author jesse
 * @Date 3/29/20 11:17 下午
 **/
public interface RedisService {
    /**
     * 生成自定义缓存key
     *
     * @param region
     * @param key
     * @return
     */
    public String getRedisKey(EnumRegion region, String key);

    /**
     * @param key
     * @return
     * @Title: getValue
     * @Description: 根据key获取value
     */
    public String getValue(String key);


    /**
     * @param key
     * @param value
     * @return
     * @Title: setValue
     * @Description: 设置key-value缓存
     */
    public String setValue(String key, String value);

    /**
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return
     * @Title: setex
     * @Description: 设置有效期的key-value缓存
     */
    public String setValueByExpireTime(String key, String value, int seconds);


    /**
     * @param key
     * @Title: delValue
     * @Description: des
     */
    public void delValue(String key);

    /**
     * 获取缓存剩余存活时间
     *
     * @param key
     * @return
     * @Title: getExpire
     * @Description: des
     */
    public Long getExpire(String key);

    /**
     * @param prefix
     * @param qry
     * @return
     * @Title: getKeys
     * @Description: deg使用的, 查找所有以prefix+"#"+qry开头的key集合
     */
    public Set<String> getKeys(String prefix, String qry);

    /**
     * @param key
     * @return 返回list的长度
     * @Title: llen
     * @Description: 查询list长度
     */
    public Long llen(String key);

    /**
     * @param key
     * @param value
     * @return push后的list长度
     * @Title: lpush
     * @Description: list左侧放入缓存
     */
    public Long lpush(String key, String value);

    /**
     * @param key
     * @param value
     * @return push后的list长度
     * @Title: rpush
     * @Description: list右侧放入缓存
     */
    public Long rpush(String key, String value);

    /**
     * @param key
     * @return key对应的list最左侧的元素
     * @Title: lpop
     * @Description: 获取list最左侧的元素
     */
    public String lpop(String key);

    /**
     * @param key
     * @return key对应的list最右侧的元素
     * @Title: rpop
     * @Description: 获取list最右侧的元素
     */
    public String rpop(String key);


    public Long incr(String key);
}
