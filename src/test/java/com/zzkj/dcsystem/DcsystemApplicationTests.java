package com.zzkj.dcsystem;

import com.zzkj.dcsystem.dao.DcOrdersGoodsMapper;
import com.zzkj.dcsystem.dao.DcOrdersMapper;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.entity.DcUser;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DcsystemApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(redisTemplate);
        System.out.println(stringRedisTemplate);
    }

    @Test
    public void test1(){
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        stringObjectObjectHashOperations.put("myhash","openid","123456");
        Object o = stringObjectObjectHashOperations.get("myhash", "openid");
        System.out.println(o);
    }

    @Autowired
    DcOrdersMapper dcOrdersMapper;

    @Test
    public void test2(){

        DcUser dcUser = new DcUser();
        dcUser.setUserId("05bf2c39-3200-4bad-b037-158c211b42d9");
        System.out.println(dcOrdersMapper);
        List<DcOrders> dcOrders = dcOrdersMapper.selectOrdersByUserId(dcUser);
        System.out.println(dcOrders);

    }

    @Autowired
    DcOrdersGoodsMapper dcOrdersGoodsMapper;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Test
    public void Test4(){
        //创建消息
        Message message = new Message("user-topic","white","你好阿，大老表".getBytes());
        try {
            SendResult send = defaultMQProducer.send(message);
            System.out.println(send);

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
