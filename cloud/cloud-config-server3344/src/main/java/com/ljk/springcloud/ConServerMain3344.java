package com.ljk.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @auther: kim
 * @create: 2020-05-20 10:08
 * @description:
 */
@SpringBootApplication
@EnableConfigServer
public class ConServerMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConServerMain3344.class, args);
    }
}
