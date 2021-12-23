package com.hqj.test.rocketmq.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送延时消息
 */
public class DelayProducer {

    public static void main(String[] args) throws Exception {
        // pg 为producerGroup ID
        DefaultMQProducer producer = new DefaultMQProducer("delay_pg");

        producer.setNamesrvAddr("jinmac.local:9876");

        producer.setRetryTimesWhenSendFailed(3);

        producer.setSendMsgTimeout(5000);

        producer.start();

        for(int i = 0; i < 100; i++) {
            byte[] body = ("Hi " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("delayProducerTopic", "delayProducerTag",body);
            msg.setKeys("key-"+i);
            msg.setDelayTimeLevel(3);
            //指定消息延迟10秒
            SendResult result = producer.send(msg);
            System.out.print(new SimpleDateFormat("mm:ss").format(new Date()));
            System.out.println(", "+result);
        }

        producer.shutdown();
    }
}
