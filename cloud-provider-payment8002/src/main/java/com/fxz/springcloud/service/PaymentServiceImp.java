package com.fxz.springcloud.service;

import com.fxz.springcloud.dao.PaymentDao;
import com.fxz.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImp implements PaymentService{

    //@Autowired Spring的
    @Resource //java自带的 两个都可以
    private PaymentDao paymentDao;


    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
