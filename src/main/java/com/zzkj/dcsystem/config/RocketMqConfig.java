package com.zzkj.dcsystem.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RocketMQ的配置类
 * @author JGZ
 * @Classname RocketMqConfig
 * @Date 2019/7/25 15:47
 * @Email 1945282561@qq.com
 */
@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConfig {

    @Autowired
    private RocketMqProperties rocketMqProperties;

    @Autowired
    private ApplicationEventPublisher publisher = null;

    private static boolean isFirstSub = true;

    private static long startTime = System.currentTimeMillis();

    @PostConstruct
    public void init() {
        System.err.println(rocketMqProperties.getNamesrvAddr());
        System.err.println(rocketMqProperties.getProducerGroupName());
        System.err.println(rocketMqProperties.getConsumerBatchMaxSize());
        System.err.println(rocketMqProperties.getConsumerGroupName());
        System.err.println(rocketMqProperties.getConsumerInstanceName());
        System.err.println(rocketMqProperties.getProducerInstanceName());
        System.err.println(rocketMqProperties.getProducerTranInstanceName());
        System.err.println(rocketMqProperties.getTransactionProducerGroupName());
        System.err.println(rocketMqProperties.isConsumerBroadcasting());
        System.err.println(rocketMqProperties.isEnableHistoryConsumer());
        System.err.println(rocketMqProperties.isEnableOrderConsumer());
        System.out.println(rocketMqProperties.getSubscribe().get(0));
    }

    /**
     * 初始化普通消息发送实例
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqProperties.getProducerGroupName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setInstanceName(rocketMqProperties.getProducerInstanceName());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        return producer;
    }

    /**
     * 创建支持事务消息的发送实例
     * @return
     * @throws MQClientException
     */
    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer(rocketMqProperties.getTransactionProducerGroupName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setInstanceName(rocketMqProperties.getProducerTranInstanceName());
        producer.setRetryTimesWhenSendAsyncFailed(10);
        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);
        producer.start();
        return producer;
    }


    /**
     * 创建消息消费的实例
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQPushConsumer pushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMqProperties.getConsumerGroupName());
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        consumer.setInstanceName(rocketMqProperties.getConsumerInstanceName());
        //判断是否是广播模式
        if(rocketMqProperties.isConsumerBroadcasting()){
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
        //设置批量消费
        consumer.setConsumeMessageBatchMaxSize(rocketMqProperties
                .getConsumerBatchMaxSize() == 0 ? 1 : rocketMqProperties
                .getConsumerBatchMaxSize());
        //获取topic和tag
        List<String> subscribeList = rocketMqProperties.getSubscribe();
        for (String sunscribe : subscribeList) {
            consumer.subscribe(sunscribe.split(":")[0], sunscribe.split(":")[1]);
        }
        //顺序消费
        if(rocketMqProperties.isEnableHistoryConsumer()){
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(
                        List<MessageExt> list, ConsumeOrderlyContext context) {
                    try {
                        context.setAutoCommit(true);
                        list = filterMessage(list);
                        if (list.size() == 0){
                            return ConsumeOrderlyStatus.SUCCESS;
                        }
                        publisher.publishEvent(new MessageEvent(list, consumer));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
        }
        //并发消费
        else{
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(
                        List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    try {
                        //过滤消息
                        list = filterMessage(list);
                        if(list.size() == 0){
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        publisher.publishEvent(new MessageEvent(list, consumer));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    consumer.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (MQClientException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return consumer;
    }

    /**
     * 过滤消息
     * @param msgs
     * @return
     */
    private List<MessageExt> filterMessage(List<MessageExt> msgs) {
        if (isFirstSub && !rocketMqProperties.isEnableHistoryConsumer()) {
            msgs = msgs.stream()
                    .filter(item -> startTime - item.getBornTimestamp() < 0)
                    .collect(Collectors.toList());
        }
        if (isFirstSub && msgs.size() > 0) {
            isFirstSub = false;
        }
        return msgs;
    }


}
