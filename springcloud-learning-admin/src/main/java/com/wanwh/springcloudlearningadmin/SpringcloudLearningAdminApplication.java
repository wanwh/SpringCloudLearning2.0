package com.wanwh.springcloudlearningadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class SpringcloudLearningAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudLearningAdminApplication.class, args);
    }

}
