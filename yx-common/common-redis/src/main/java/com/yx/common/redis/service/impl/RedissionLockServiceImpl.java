package com.yx.common.redis.service.impl;

import com.yx.common.redis.service.RedissionLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @encoding UTF-8
 * @time 2019/7/15 3:35 PM
 * @Description
 */
@Primary
@Component
public class RedissionLockServiceImpl implements RedissionLockService {

    @Autowired
    private RedissonClient redissonClient;//RedissonClient已经由配置类生成，这里自动装配即可

    /**
     * 加锁
     * <br>lock(), 拿不到lock就不罢休，不然线程就一直block
     *
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    @Override
    public RLock lock(String lockKey, int timeout) {
        return this.lock(lockKey, TimeUnit.SECONDS, timeout);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁(单位：秒)
     *
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return this.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁
     * <br>tryLock()，马上返回，拿到lock就返回true，不然返回false
     * <br>带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }


    /**
     * 释放锁
     *
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
