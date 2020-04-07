package com.yx.game.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yx.common.base.restful.GlobalResponse;
import com.yx.common.mvc.utils.ResponseUtil;
import com.yx.common.redis.enums.EnumRegion;
import com.yx.common.redis.service.RedisService;
import com.yx.common.redis.service.RedissionLockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jesse
 * @Date 3/31/20 1:30 下午
 **/
@Slf4j
@Api(tags = "redis测试")
@ApiSort(2)
@RestController
public class RedisTestController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissionLockService redissionLockService;

    /**
     * redis缓存和Gson
     *
     * @return
     */
    @ApiOperation(value = "redis缓存", notes = "redis缓存测试")
    @GetMapping(value = "/testCache")
    public GlobalResponse printgetCache() {
        String key = redisService.getRedisKey(EnumRegion.NOMORL_REGION, "List");
        String value = "{\"name\":\"安可\",\"age\":29}";
        redisService.setValue(key, value);
        return ResponseUtil.success(value);
    }

    /**
     * redis分布式锁
     *
     * @return
     */
    @ApiOperation(value = "redis分布式锁", notes = "redis分布式锁测试")
    @GetMapping(value = "/testLock")
    public GlobalResponse printtestLock() throws InterruptedException {

        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("=============线程开启============" + Thread.currentThread().getName());
                        redissionLockService.lock(key, 2); //直接加锁，获取不到锁则一直等待获取锁
                        Thread.sleep(100); //获得锁之后可以进行相应的处理
                        System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
//                        redissionLockUtils.unlock(key);  //解锁
                        System.err.println("=============================" + Thread.currentThread().getName());
//                        boolean isGetLock =  redissionLockService.tryLock(key,TimeUnit.SECONDS,1,10); //尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
//                        if(isGetLock){
//                            Thread.sleep(1001); //获得锁之后可以进行相应的处理
//                            System.err.println("======获得锁后进行相应的操作======"+Thread.currentThread().getName());
//                            redissionLockUtils.unlock(key);
//                            System.err.println("============================="+Thread.currentThread().getName());
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        Thread.sleep(20 * 1000L);

        return ResponseUtil.success();
    }

}
