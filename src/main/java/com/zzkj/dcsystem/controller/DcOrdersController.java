package com.zzkj.dcsystem.controller;

import com.google.gson.Gson;
import com.zzkj.dcsystem.dto.*;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.service.DcOrdersService;
import com.zzkj.dcsystem.service.impl.DcOrdersServiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:52
**/

@Controller
public class DcOrdersController {

    @Autowired
    private DcOrdersServiceServiceImpl ordersService;

    @Autowired
    private DcOrdersService dcOrdersService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());
  
    @RequestMapping("/order/insertOrder.action")
    public @ResponseBody Message insertOrder(@RequestBody OrdersGoodsDto ordersGoodsDto){

        OrdersDto orders =new OrdersDto();
        orders.setOrdersId(UUID.randomUUID().toString());
        orders.setUserId(ordersGoodsDto.getUserId());
        orders.setLinkMan(ordersGoodsDto.getLinkMan());
        orders.setPhone(ordersGoodsDto.getPhone());
        orders.setCreateDate(ordersGoodsDto.getCreateDate());
        orders.setNote(ordersGoodsDto.getNote());
        orders.setStoreAddress(ordersGoodsDto.getStoreAddress());
        orders.setUserAddress(ordersGoodsDto.getUserAddress());
        orders.setCombined(ordersGoodsDto.getCombined());
        orders.setEvaluation(ordersGoodsDto.getEvaluation());
        if (orders!=null){
            boolean b = ordersService.insertOrder(orders);
            List<ShopCartGoodsDto> shopCartGoodsDtos = new ArrayList<>();
            shopCartGoodsDtos = ordersGoodsDto.getGoods();
            for (ShopCartGoodsDto shopCartGoodsDto : shopCartGoodsDtos) {
                String goodsId = shopCartGoodsDto.getGoodsId();
                Integer amount = shopCartGoodsDto.getAmount();
                Float total = shopCartGoodsDto.getTotal();
                MyOrdersDto ordersGoods = new MyOrdersDto();
                ordersGoods.setOrdersId(orders.getOrdersId());
                ordersGoods.setGoodsId(goodsId);
                ordersGoods.setAmount(amount);
                ordersGoods.setTotal(total);
                boolean orderGoodsFlag = ordersService.insertOrderGoods(ordersGoods);
            }
            if (b){
                return new Message("success","订单已完成");
            }else {
                return new Message("error","订单未完成");

            }
        }

        return new Message("error","订单信息为空！");

    }
  
  
    @RequestMapping("/order/selectOrder.action")
    public @ResponseBody List<OrdersDto> selectOrder(@RequestBody DcOrders orders) {
        List<OrdersDto> ordersList = ordersService.selectOrderByUserId(orders.getUser().getUserId());
        return ordersList;

    }

    /**
     * 获取用户的订单信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/OrdersList")
    public ResponseEntity getDcOrdersList(String userId){
        //看缓存中是否有该用户的订单信息
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String json = ops.get("DcOrdersList:"+ userId);
        List<DcOrders> dcOrdersList = null;
//        logger.info("用户:"+userId+"查询订单信息");
        if(json == null || "".equals(json)){
            //如果没有
            //根据用户id获取订单列表
            dcOrdersList = dcOrdersService.getDcOrdersListByUserId(userId);
            //放入缓存
            this.redisCache(userId,dcOrdersList);
//            logger.info("用户:从数据库中获取");
        }
        else{
            dcOrdersList = new Gson().fromJson(json,List.class);
//            logger.info("用户:从缓存中获取");
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
        ops.set( "DcOrdersList:"+ userId,json);
        //设置7天过期
        stringRedisTemplate.expire("DcOrdersList:"+ userId  ,604800, TimeUnit.SECONDS);
    }

}
