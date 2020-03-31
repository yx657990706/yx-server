package com.yx.common.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @author jesse
 * @Description: 读取application.properties中的属性, 设置redis连接
 * @date 2018-04-26 21:17:25
 */
@Configuration
@EnableAutoConfiguration
//继承CachingConfigurerSupport，为了自定义生成KEY的策略。可以不继承。
public class JedisPoolConnectionConfig extends CachingConfigurerSupport {
    private Logger log = LoggerFactory.getLogger(JedisPoolConnectionConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.jedis.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.jedis.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;


    /**
     * 单节点（redis连接池）
     * @return
     */
    @Bean
    public JedisPool redisPoolFactory() {
        log.info("========redis地址：" + host + ":" + port);
        // 建立连接池配置参数
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);// 设置最大连接数
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis); // 设置最大阻塞时间，记住是毫秒数milliseconds

        // 创建连接池
        JedisPool jedisPool;
        if(StringUtils.isBlank(password)){
            log.debug("========redis 免密连接========");
            jedisPool = new JedisPool(jedisPoolConfig, host, port);
        }else{
            log.debug("========redis 认证连接========");
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
        }
        log.info("========redis连接成功！！========");
        return jedisPool;
    }

    /**
     * redis集群(内部使用连接池)
     * @return
     */
//    @Bean
//    public JedisCluster getJedisCluster() {
//        log.info("========JedisCluster注入成功！！========");
//        String[] cNodes = clusterNodes.split(",");
//        Set<HostAndPort> nodes =new HashSet<>();
//        //分割出集群节点
//        for(String node : cNodes) {
//            String[] hp = node.split(":");
//            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
//            log.info("========JedisCluster节点：" + hp[0] + ":" + hp[1]);
//        }
//        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//
//        return new JedisCluster(nodes,timeout,jedisPoolConfig);
//    }

    /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    if (obj != null) {
                        sb.append(obj.toString());
                    } else {
                        sb.append("null");
                    }
                }
                log.debug("===>>keyGenerator=" + sb.toString());
                return sb.toString();
            }
        };
    }

}
