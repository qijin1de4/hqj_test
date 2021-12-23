package com.hqj.test.rocketmq.general;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class OnewayProducer {
    public static void main(String[] args) throws Exception {

        // pg 为producerGroup ID
        DefaultMQProducer producer = new DefaultMQProducer("oneway_pg");

        producer.setNamesrvAddr("jinmac.local:9876");

        producer.setRetryTimesWhenSendFailed(3);

        producer.setSendMsgTimeout(5000);


        producer.start();

        for(int i = 0; i < 100; i++) {
            byte[] body = ("Hi " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("onewayProducerTopic", "onwayTag",body);
            msg.setKeys("key-"+i);
            producer.sendOneway(msg); // sendOneWay方法发送单向消息
        }

        producer.shutdown();
    }
}
