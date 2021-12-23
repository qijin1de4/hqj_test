package com.hqj.test.rocketmq.general;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {

    public static void main(String[] args) throws Exception {

        // pg 为producerGroup ID
        DefaultMQProducer producer = new DefaultMQProducer("pg");

        producer.setNamesrvAddr("jinmac.local:9876");

        producer.setRetryTimesWhenSendAsyncFailed(3);

        //指定新新创建的Topic的Queue的数量为2，默认为4
        producer.setDefaultTopicQueueNums(2);

        producer.setSendMsgTimeout(5000);


        producer.start();

        for(int i = 0; i < 100; i++) {
            byte[] body = ("Hi " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("asyncProducerTopic", "asyncProducerTag",body);
            msg.setKeys("key-"+i);
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(3); // 等待3秒，消息发送完毕后再关闭producer
        producer.shutdown();
    }
}
