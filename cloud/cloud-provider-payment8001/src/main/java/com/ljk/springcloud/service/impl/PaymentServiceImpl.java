package com.ljk.springcloud.service.impl;

import com.ljk.springcloud.dao.PaymentDao;
import com.ljk.springcloud.entities.Payment;
import com.ljk.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther: kim
 * @create: 2020-05-15 09:17
 * @description:
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment)
    {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id)
    {
        return paymentDao.getPaymentById(id);
    }
}
