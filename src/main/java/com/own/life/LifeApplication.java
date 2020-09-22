package com.own.life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling // 这里，启用定时任务
public class LifeApplication {

    public static void main(String[] args) {

        SpringApplication.run(LifeApplication.class, args);
    }

}
