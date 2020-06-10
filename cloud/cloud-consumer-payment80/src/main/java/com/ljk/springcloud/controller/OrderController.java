package com.ljk.springcloud.controller;

import com.ljk.myrule.MyRule;
import com.ljk.springcloud.common.CommonResult;
import com.ljk.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @auther: kim
 * @create: 2020-05-15 09:55
 * @description:
 */
@RestController
@Slf4j
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MyRule.class)
public class OrderController {

    @Resource
    private RestTemplate restTemplate;
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
//    private static final String PAYMENT_URL = "http://localhost:8001";

    @PostMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

}
