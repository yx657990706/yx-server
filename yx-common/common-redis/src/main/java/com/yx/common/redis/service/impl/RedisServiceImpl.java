package com.yx.common.redis.service.impl;

import com.yx.common.redis.service.RedisService;
import com.yx.common.redis.enums.EnumRegion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author jesse
 * @Description: jedis工具类, 用自动注入的方式使用
 * @date 2018-04-24 23:32:11
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 生成自定义缓存key
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
        Jedis jedis = jedisPool.getResource();
        String str;
        try {
            str = jedis.get(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        String str;
        try {
            str = jedis.set(key, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        String str;
        try {
            str = jedis.setex(key, seconds, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return str;
    }


    /**
     * @param key
     * @Title: delValue
     * @Description: des
     */
    @Override
    public void delValue(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        Long ttl;
        try {
            ttl = jedis.ttl(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        Set<String> set;
        try {
            set = jedis.keys(prefix + "#" + (null == qry ? "" : qry) + "*");
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        Long len;
        try {
            len = jedis.llen(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if (null == len) {
            len = 0L;
        }
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
        Jedis jedis = jedisPool.getResource();
        Long len;
        try {
            len = jedis.lpush(key, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        Long len;
        try {
            len = jedis.rpush(key, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();
        String str;
        try {
            str = jedis.lpop(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
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
        Jedis jedis = jedisPool.getResource();

        String str;
        try {
            str = jedis.rpop(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return str;
    }


    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long value;
        try {
            value = jedis.incr(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return value;
    }

}
