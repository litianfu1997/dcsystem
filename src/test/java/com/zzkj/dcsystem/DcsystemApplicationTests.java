package com.zzkj.dcsystem;

import com.zzkj.dcsystem.dao.DcOrdersGoodsMapper;
import com.zzkj.dcsystem.dao.DcOrdersMapper;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.entity.DcUser;
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
    @Test
    public void test3(){

        DcOrders dcOrders = new DcOrders();
        dcOrders.setOrdersId("05bf2c39-3200-4bad-b037-158c211b42d9");
        List<DcOrdersGoods> dcOrdersGoods = dcOrdersGoodsMapper.selectDcOrdersGoodsByDcOrders(dcOrders);
        System.out.println(dcOrdersGoods);

    }
}
