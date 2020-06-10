package com.ljk.springcloud.controller;

import com.ljk.springcloud.common.CommonResult;
import com.ljk.springcloud.entities.Payment;
import com.ljk.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther: kim
 * @create: 2020-05-15 09:19
 * @description:
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****查入结果数：" + result);
        if (result > 0) {
            return new CommonResult(200, "成功", result);
        }
        return new CommonResult(444, "失败", result);
    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200, "查询成功,serverPort:  " + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录,查询ID: " + id, null);
        }
    }
    @GetMapping("/payment/lb")
    public String lb() {
        return serverPort;
    }
}