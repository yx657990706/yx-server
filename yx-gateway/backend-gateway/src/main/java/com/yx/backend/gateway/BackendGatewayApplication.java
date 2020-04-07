package com.yx.backend.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.yx.common", "com.yx.backend.gateway"})
public class BackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendGatewayApplication.class, args);
    }

}
