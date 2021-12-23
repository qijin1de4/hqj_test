package com.hqj.test.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TestTransactionListener implements TransactionListener {

    /**
     * 消息回调操作方法
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("预提交消息成功"+message.getTags());
        if(StringUtils.equals("txTagA", message.getTags())) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if(StringUtils.equals("txTagB", message.getTags())) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else if(StringUtils.equals("txTagC", message.getTags())) {
            return LocalTransactionState.UNKNOW;
        }

        return LocalTransactionState.UNKNOW;
    }

    /**
     * 回查回查方法
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("执行消息回查："+messageExt.getTags());

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
