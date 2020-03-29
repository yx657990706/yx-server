package com.yx.commonredis.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Author jesse
 * @Date 3/29/20 11:23 下午
 **/
public interface RedissionLockService {
    /**
     * 加锁
     * <br>lock(), 拿不到lock就不罢休，不然线程就一直block
     *
     * @param lockKey
     * @return
     */
    public RLock lock(String lockKey) ;

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public RLock lock(String lockKey, int timeout) ;

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    public RLock lock(String lockKey, TimeUnit unit, int timeout) ;

    /**
     * 尝试获取锁(单位：秒)
     *
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) ;

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
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) ;


    /**
     * 释放锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey) ;

    /**
     * 释放锁
     *
     * @param lock
     */
    public void unlock(RLock lock);
}
