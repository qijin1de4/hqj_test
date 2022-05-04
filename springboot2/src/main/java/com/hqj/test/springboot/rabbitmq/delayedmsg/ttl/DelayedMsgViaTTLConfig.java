package com.hqj.test.springboot.rabbitmq.delayedmsg.ttl;

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

//@Configuration
public class DelayedMsgViaTTLConfig {

    private static final Logger log = LoggerFactory.getLogger(DelayedMsgViaTTLConfig.class);

    public static final String TTL_EXCHANGE = "ttlExchange";

    public static final String TTL_EXCHANGE_DEAD = "ttlExchangeDead";

    public static final String TTL_QUEUE_A = "ttlQueueA";

    public static final String TTL_QUEUE_B = "ttlQueueB";

    public static final String TTL_QUEUE_DEAD = "ttlQueueDead";

    public static final String TTL_ROUTING_KEY_A = "ttlRoutingKeyA";

    public static final String TTL_ROUTING_KEY_B = "ttlRoutingKeyB";

    public static final String TTL_ROUTING_KEY_DEAD = "ttlRoutingKeyDead";

    @Bean(TTL_EXCHANGE)
    public DirectExchange ttlExchangeA() {
        return ExchangeBuilder.directExchange(TTL_EXCHANGE).durable(false).build();
    }

    @Bean(TTL_EXCHANGE_DEAD)
    public DirectExchange ttlExchangeDead() {
        return ExchangeBuilder.directExchange(TTL_EXCHANGE_DEAD).durable(false).build();
    }

    @Bean(TTL_QUEUE_A)
    public Queue ttlQueueA() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", TTL_EXCHANGE_DEAD);
        args.put("x-dead-letter-routing-key", TTL_ROUTING_KEY_DEAD);
        // 设置消息TTL 单位ms
        args.put("x-message-ttl", 10000);
        return QueueBuilder.nonDurable(TTL_QUEUE_A).withArguments(args).build();
    }

    @Bean(TTL_QUEUE_B)
    public Queue ttlQueueB() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", TTL_EXCHANGE_DEAD);
        args.put("x-dead-letter-routing-key", TTL_ROUTING_KEY_DEAD);
        // 设置消息TTL 单位ms
        args.put("x-message-ttl", 40000);
        return QueueBuilder.nonDurable(TTL_QUEUE_B).withArguments(args).build();
    }

    // 死信队列
    @Bean(TTL_QUEUE_DEAD)
    public Queue ttlQueueDead() {
        return QueueBuilder.nonDurable(TTL_QUEUE_DEAD).build();
    }

    // 绑定
    @Bean
    public Binding ttlQueueABinding(@Qualifier(TTL_QUEUE_A)Queue ttlQueueA, @Qualifier(TTL_EXCHANGE) DirectExchange ttlExchange) {
        return BindingBuilder.bind(ttlQueueA).to(ttlExchange).with(TTL_ROUTING_KEY_A);
    }

    @Bean
    public Binding ttlQueueBBinding(@Qualifier(TTL_QUEUE_B)Queue ttlQueueB, @Qualifier(TTL_EXCHANGE) DirectExchange ttlExchange) {
        return BindingBuilder.bind(ttlQueueB).to(ttlExchange).with(TTL_ROUTING_KEY_B);
    }

    @Bean
    public Binding ttlQueueDeadBinding(@Qualifier(TTL_QUEUE_DEAD)Queue ttlQueueDEAD, @Qualifier(TTL_EXCHANGE_DEAD) DirectExchange ttlExchangeDead) {
        return BindingBuilder.bind(ttlQueueDEAD).to(ttlExchangeDead).with(TTL_ROUTING_KEY_DEAD);
    }

    // 消费者
    @Bean
    public Object deadLetterQueueConsumer() {
        return new Object() {
          @RabbitListener(queues = TTL_QUEUE_DEAD)
          public void receiveMsg(Message message, Channel channel) {
                log.info("当前时间{}, 收到死信队列消息：{}", new Date(), new String (message.getBody()));
          }
        };
    }
}

