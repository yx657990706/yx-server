package com.yx.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//扫描引用的common配置
@SpringBootApplication(scanBasePackages = {"com.yx.common", "com.yx.game"})
public class YxGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxGameApplication.class, args);
    }

}
