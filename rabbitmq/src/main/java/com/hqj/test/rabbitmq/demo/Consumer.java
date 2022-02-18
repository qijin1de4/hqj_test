package com.hqj.test.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.hqj.test.rabbitmq.demo.Producer.QUEUE_NAME;

public class Consumer {
    public static void main(String[] args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("localhost");

        final Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();

        channel.basicConsume(QUEUE_NAME, (consumerTag, message) -> System.out.println(new String(message.getBody())), consumerTag -> System.out.println("message consume has been cancelled!"));

    }
}
