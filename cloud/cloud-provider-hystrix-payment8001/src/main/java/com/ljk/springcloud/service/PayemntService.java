package com.ljk.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @auther: kim
 * @create: 2020-05-17 11:05
 * @description:
 */
@Service
public class PayemntService {

    public String paymentInfo_OK(int id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_ok,id:" + id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {@HystrixProperty(name =
            "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_Timeout(int id) {
        long l = System.currentTimeMillis();
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout,id:" + id + " 耗时：" + (System.currentTimeMillis() - l);
    }

    public String paymentInfo_TimeoutHandler(int id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeoutHandler,id:" + id + " ，服务繁忙，请稍后再试";
    }
}
