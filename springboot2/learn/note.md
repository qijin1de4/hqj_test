
###

---

===

RabbitMQ

**通过死信队列实现延时消息:** 

![通过TTL与死信队列实现延时消息](/Users/qijinhu/idea_projects/hqj_test/springboot2/learn/delayed_msg_by_ttl_and_dead_letter.png "结构图")

package:
com.hqj.test.springboot.rabbitmq.ttlmsg

**通过延迟交换机实现延时消息:** 
rabbitmq安装延迟消息交换机插件（rabbitmq_delayed_message_exchange）
实现基于延迟交换机的延时消息投递


