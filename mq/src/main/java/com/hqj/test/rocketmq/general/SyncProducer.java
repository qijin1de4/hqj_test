package com.hqj.test.rocketmq.general;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class SyncProducer {

    public static void main(String[] args) throws Exception {

        // pg 为producerGroup ID
        DefaultMQProducer producer = new DefaultMQProducer("pg");

        producer.setNamesrvAddr("jinmac.local:9876");

        producer.setRetryTimesWhenSendFailed(3);

        producer.setSendMsgTimeout(5000);


        producer.start();

        for(int i = 0; i < 100; i++) {
            byte[] body = ("Hi " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("someTopic", "someTag",body);
            msg.setKeys("key-"+i);
            SendResult result = producer.send(msg);
            System.out.println(result);
        }

        producer.shutdown();
    }
}
