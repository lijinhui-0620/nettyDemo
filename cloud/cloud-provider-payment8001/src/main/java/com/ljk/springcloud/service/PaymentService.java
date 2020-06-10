package com.ljk.springcloud.service;

import com.ljk.springcloud.entities.Payment;

/**
 * @auther: kim
 * @create: 2020-05-15 09:17
 * @description:
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
