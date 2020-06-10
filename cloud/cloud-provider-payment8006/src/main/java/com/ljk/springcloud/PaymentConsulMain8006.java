package com.ljk.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: kim
 * @create: 2020-05-16 11:27
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class PaymentConsulMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsulMain8006.class, args);
    }

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/consul")
    public String paymentZk() {
        return "spring cloud with consul:" + serverPort + "\t" + RandomStringUtils.randomAlphabetic(32).toLowerCase();
    }
}
