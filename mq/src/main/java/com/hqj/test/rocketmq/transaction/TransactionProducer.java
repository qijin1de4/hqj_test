package com.hqj.test.rocketmq.transaction;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionProducer {

    public static void main(String[] args) throws Exception {

        TransactionMQProducer producer = new TransactionMQProducer("tx_pg");

        producer.setExecutorService(new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("tx-producer-check-thread");
                    return thread;
                }));

        producer.setNamesrvAddr("jinmac.local:9876");

        producer.setTransactionListener(new TestTransactionListener());

        producer.start();

        String[] tags = {"txTagA", "txTagB", "txTagC"};

        for(int i=0; i < 3; i++) {
            byte[] body = ("Hi " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("txTopic1", tags[i], body);
            SendResult result = producer.sendMessageInTransaction(msg, null);
            System.out.println(result);
        }

//        producer.shutdown();
    }
}
