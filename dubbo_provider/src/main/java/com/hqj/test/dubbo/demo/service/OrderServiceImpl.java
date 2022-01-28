package com.hqj.test.dubbo.demo.service;

import com.hqj.test.dubbo.demo.OrderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Random;

@DubboService
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public int getOrderId() {
        final Random random = new Random();
        return random.nextInt(999);
    }

}
