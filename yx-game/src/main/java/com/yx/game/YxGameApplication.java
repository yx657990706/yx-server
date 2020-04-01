package com.yx.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient//注册发现
@MapperScan(value = {"com.yx.game.dao"})//tkMybatis扫描
@SpringBootApplication(scanBasePackages = {"com.yx.common", "com.yx.game"})//扫描引用的common配置
public class YxGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxGameApplication.class, args);
    }

}
