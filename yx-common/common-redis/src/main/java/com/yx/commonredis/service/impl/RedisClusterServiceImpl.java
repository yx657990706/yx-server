package com.yx.commonredis.service.impl;

import com.yx.commonredis.enums.EnumRegion;
import com.yx.commonredis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2019/1/24
 * @time 5:19 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
//TODO 注销了redis集群工具类
//@Component
public class RedisClusterServiceImpl implements RedisService {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 生成自定义缓存key
     *
     * @param region
     * @param key
     * @return
     */
    @Override
    public String getRedisKey(EnumRegion region, String key) {
        return "CACHE:" + region + ":" + key;
    }

    /**
     * @param key
     * @return
     * @Title: getValue
     * @Description: 根据key获取value
     */
    @Override
    public String getValue(String key) {
        String str = jedisCluster.get(key);
        return str;
    }


    /**
     * @param key
     * @param value
     * @return
     * @Title: setValue
     * @Description: 设置key-value缓存
     */
    @Override
    public String setValue(String key, String value) {
        String str = jedisCluster.set(key, value);
        return str;
    }

    /**
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return
     * @Title: setex
     * @Description: 设置有效期的key-value缓存
     */
    @Override
    public String setValueByExpireTime(String key, String value, int seconds) {
        String str = jedisCluster.setex(key, seconds, value);
        return str;
    }


    /**
     * @param key
     * @Title: delValue
     * @Description: des
     */
    @Override
    public void delValue(String key) {
        jedisCluster.del(key);
    }

    /**
     * 获取缓存剩余存活时间
     *
     * @param key
     * @return
     * @Title: getExpire
     * @Description: des
     */
    @Override
    public Long getExpire(String key) {
        Long ttl = jedisCluster.ttl(key);

        return ttl;
    }

    /**
     * @param prefix
     * @param qry
     * @return
     * @Title: getKeys
     * @Description: deg使用的, 查找所有以prefix+"#"+qry开头的key集合
     */
    @Override
    public Set<String> getKeys(String prefix, String qry) {

        Set<String> set = jedisCluster.hkeys(prefix + "#" + (null == qry ? "" : qry) + "*");
        return set;
    }

    /**
     * @param key
     * @return 返回list的长度
     * @Title: llen
     * @Description: 查询list长度
     */
    @Override
    public synchronized Long llen(String key) {
        Long len = jedisCluster.llen(key);
        return len;
    }

    /**
     * @param key
     * @param value
     * @return push后的list长度
     * @Title: lpush
     * @Description: list左侧放入缓存
     */
    @Override
    public synchronized Long lpush(String key, String value) {
        Long len = jedisCluster.lpush(key, value);
        if (null == len) {
            len = 0L;
        }
        return len;
    }

    /**
     * @param key
     * @param value
     * @return push后的list长度
     * @Title: rpush
     * @Description: list右侧放入缓存
     */
    @Override
    public synchronized Long rpush(String key, String value) {
        Long len = jedisCluster.rpush(key, value);
        if (null == len) {
            len = 0L;
        }
        return len;
    }

    /**
     * @param key
     * @return key对应的list最左侧的元素
     * @Title: lpop
     * @Description: 获取list最左侧的元素
     */
    @Override
    public synchronized String lpop(String key) {
        String str = jedisCluster.lpop(key);
        return str;
    }

    /**
     * @param key
     * @return key对应的list最右侧的元素
     * @Title: rpop
     * @Description: 获取list最右侧的元素
     */
    @Override
    public synchronized String rpop(String key) {
        String str = jedisCluster.rpop(key);
        return str;
    }

    @Override
    public Long incr(String key) {
        Long value = jedisCluster.incr(key);
        return value;
    }
}
