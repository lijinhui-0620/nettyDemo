package com.ljk.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther: kim
 * @create: 2020-05-23 11:15
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMain83 {
    public static void main(String[] args){
        SpringApplication.run(OrderMain83.class,args);
    }
}
