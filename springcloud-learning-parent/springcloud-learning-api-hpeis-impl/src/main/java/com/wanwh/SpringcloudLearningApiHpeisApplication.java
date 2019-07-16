package com.wanwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SpringcloudLearningApiHpeisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudLearningApiHpeisApplication.class,args);
    }
}
