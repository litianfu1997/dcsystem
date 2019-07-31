package com.zzkj.dcsystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * rocketMQ.properties配置文件的类
 * @author JGZ
 * @Classname RocketMQroperties
 * @Date 2019/7/25 15:43
 * @Email 1945282561@qq.com
 */
@PropertySource("classpath:rocketMQ.properties")
@ConfigurationProperties(prefix = "suning.rocketmq")
@Configuration
@Data
public class RocketMqProperties {
    private String namesrvAddr;
    private String producerGroupName;
    private String transactionProducerGroupName;
    private String consumerGroupName;
    private String producerInstanceName;
    private String consumerInstanceName;
    private String producerTranInstanceName;
    private int consumerBatchMaxSize;
    private boolean consumerBroadcasting;
    private boolean enableHistoryConsumer;
    private boolean enableOrderConsumer;
    private List<String> subscribe = new ArrayList<String>();

}
