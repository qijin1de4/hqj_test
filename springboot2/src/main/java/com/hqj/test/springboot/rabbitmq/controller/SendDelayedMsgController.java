package com.hqj.test.springboot.rabbitmq.controller;

import com.hqj.test.springboot.rabbitmq.delayedmsg.delayedexchange.DelayedMsgViaExchangeConfig;
import com.hqj.test.springboot.rabbitmq.delayedmsg.ttl.DelayedMsgViaTTLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.zip.DataFormatException;

//@RestController
//@RequestMapping("/delayedMsg")
public class SendDelayedMsgController {

    private static final Logger log = LoggerFactory.getLogger(SendDelayedMsgController.class);

    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/viaTTL/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {

        log.info("发送消息[{}]给两个TTL队列！", message);

        rabbitTemplate.convertAndSend(DelayedMsgViaTTLConfig.TTL_EXCHANGE, DelayedMsgViaTTLConfig.TTL_ROUTING_KEY_B, "发送给TTL为40秒的队列B消息：["+message+"], 时间：" + new Date());

        rabbitTemplate.convertAndSend(DelayedMsgViaTTLConfig.TTL_EXCHANGE, DelayedMsgViaTTLConfig.TTL_ROUTING_KEY_A, "发送给TTL为10秒的队列A消息：["+message+"], 时间：" + new Date());
    }

    @GetMapping("/viaDelayedExchange/sendMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        log.info("当前时间：{}, 发送消息[{}]与延时[{}]毫秒", new Date(), message, delayTime);

        rabbitTemplate.convertAndSend(DelayedMsgViaExchangeConfig.DELAYED_EXCHANGE, DelayedMsgViaExchangeConfig.DELAYED_ROUTING_KEY,
                message, msg -> {
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
        });
    }

}
