package com.hqj.test.dubbo.demo.controller;

import com.hqj.test.dubbo.demo.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @DubboReference(check = false)
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("/demo")
    public String demo() {
        return " demo orderid : "+orderService.getOrderId();
    }
}
