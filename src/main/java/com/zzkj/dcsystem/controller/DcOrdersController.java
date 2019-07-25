package com.zzkj.dcsystem.controller;

import com.google.gson.Gson;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.service.DcOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单业务
 * @author JGZ
 * @Classname DcOrdersController
 * @Date 2019/7/24 16:14
 * @Email 1945282561@qq.com
 */
@Controller
public class DcOrdersController {

    @Autowired
    private DcOrdersService dcOrdersService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取用户的订单信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/OrdersList")
    public ResponseEntity getDcOrdersList(String userId){
        //看缓存中是否有该用户的订单信息
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String json = ops.get(userId + ":DcOrdersList");
        List<DcOrders> dcOrdersList = null;
        logger.info("用户:"+userId+"查询订单信息");
        if(json == null || "".equals(json)){
            //如果没有
            //根据用户id获取订单列表
            dcOrdersList = dcOrdersService.getDcOrdersListByUserId(userId);
            //放入缓存
            this.redisCache(userId,dcOrdersList);
            logger.info("用户:从数据库中获取");
        }
        else{
            dcOrdersList = new Gson().fromJson(json,List.class);
            logger.info("用户:从缓存中获取");
        }
        //返回
        return ResponseEntity.status(HttpStatus.OK).body(dcOrdersList);
    }

    /**
     * 缓存用户的userId和订单列表
     * @param userId
     * @param dcOrdersList
     */
    private void redisCache(String userId,List<DcOrders> dcOrdersList){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //将订单列表json化
        Gson gson = new Gson();
        String json = gson.toJson(dcOrdersList);
        //放入缓存中
        ops.set(userId + ":DcOrdersList",json);
        //设置7天过期
        stringRedisTemplate.expire(userId + ":DcOrdersList",604800, TimeUnit.SECONDS);
    }

    public DcOrdersService getDcOrdersService() {
        return dcOrdersService;
    }

    public void setDcOrdersService(DcOrdersService dcOrdersService) {
        this.dcOrdersService = dcOrdersService;
    }
}
