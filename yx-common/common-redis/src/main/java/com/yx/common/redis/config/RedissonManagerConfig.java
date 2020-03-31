package com.yx.common.redis.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @encoding UTF-8
 * @time 2019/7/15 3:18 PM
 * @Description
 */
@Slf4j
@Configuration
public class RedissonManagerConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;


    /**
     * 单机
     *
     * @return
     */
    @Bean
    public RedissonClient redissonSingle() {
        log.info("========Redission地址：" + host + ":" + port);
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+host + ":" + port)
                .setPassword(password);
        RedissonClient redisson = Redisson.create(config);
        log.debug("========Redission初始化成功========");
        log.debug("redission config:{}", JSON.toJSONString(redisson.getConfig()));
        //可通过打印JSON.toJSONString(redisson.getConfig())来检测是否配置成功
        return redisson;
    }

//    /**
//     * 集群配置
//     *
//     * @return
//     */
//    @Bean
//    public RedissonClient redissonCluster() {
//        String[] nodes = clusterNodes.split(",");
//        //redisson版本是3.11，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        for (int i = 0; i < nodes.length; i++) {
//            nodes[i] = "redis://" + nodes[i];
//        }
//        RedissonClient redisson = null;
//        Config config = new Config();
//        config.useClusterServers() //这是用的集群server
//                .setScanInterval(2000) //设置集群状态扫描时间
//                .addNodeAddress(nodes);
////                .setPassword(password);
//        redisson = Redisson.create(config);
//
//        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
//        return redisson;
//    }

//    /**
//     * 哨兵
//     * @return
//     */
//    @Bean
//    public RedissonClient redissonSentinel() {
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName("mymaster")
//                .addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379")
//                .addSentinelAddress("127.0.0.1:26319");
//        //可以设置连接池等
//        RedissonClient redisson = Redisson.create(config);
//
//        return redisson;
//    }

//    /**
//     * 主从
//     *
//     * @return
//     */
//    @Bean
//    public RedissonClient redissonSentinel() {
//        Config config = new Config();
//        config.useMasterSlaveServers()
//                .setMasterAddress("127.0.0.1:6379")
//                .addSlaveAddress("127.0.0.1:6389", "127.0.0.1:6332", "127.0.0.1:6419")
//                .addSlaveAddress("127.0.0.1:6399");
//        RedissonClient redisson = Redisson.create(config);
//
//        return redisson;
//    }
}
