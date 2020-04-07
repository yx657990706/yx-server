package com.yx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.yx.common", "com.yx.gateway"})
public class YxGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxGatewayApplication.class, args);
    }

}
