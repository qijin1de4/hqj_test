package com.hqj.test.springboot.rabbitmq.delayedmsg.delayedexchange;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedMsgViaExchangeConfig {

    private static final Logger log = LoggerFactory.getLogger(DelayedMsgViaExchangeConfig.class);

    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    public static final String DELAYED_EXCHANGE = "delayed.exchange";

    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @Bean(DELAYED_QUEUE_NAME)
    public Queue delayedQueue() {
        return QueueBuilder.nonDurable(DELAYED_QUEUE_NAME).build();
    }

    @Bean(DELAYED_EXCHANGE)
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", false, false, args);
    }

    @Bean
    public Binding delayedBinding(@Qualifier(DELAYED_QUEUE_NAME) Queue delayedQueue, @Qualifier(DELAYED_EXCHANGE) CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

    @Bean
    public Object delayedConsumer() {
        return new Object() {
            @RabbitListener(queues = DELAYED_QUEUE_NAME)
            public void receiveMsg(Message message, Channel channel) {
                log.info("当前时间{}, 收到延迟队列消息：{}", new Date(), new String (message.getBody()));
            }
        };
    }
}
