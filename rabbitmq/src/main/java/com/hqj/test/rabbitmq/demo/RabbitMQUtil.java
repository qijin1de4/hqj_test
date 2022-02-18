package com.hqj.test.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {

    public static Channel getChannel() throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("localhost");

        return factory.newConnection().createChannel();

    }
}
