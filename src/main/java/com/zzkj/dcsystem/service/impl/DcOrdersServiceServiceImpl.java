package com.zzkj.dcsystem.service.impl;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.zzkj.dcsystem.dao.DcOrderMapper;
import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.service.IDcOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:50
 */
@Service
public class DcOrdersServiceServiceImpl implements IDcOrdersService {

    @Autowired
    private DcOrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 插入订单
     *
     * @param orders
     * @return
     */
    @Override
    public boolean insertOrder(OrdersDto orders) {
        boolean b = orderMapper.insertOrder(orders);
        return b;
    }

    /**
     * 通过userId查询订单
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrdersDto> selectOrderByUserId(String userId) {
        List<OrdersDto> ordersList = orderMapper.selectOrderByUserId(userId);
        return ordersList;
    }

    /**
     * 插入订单的商品列表
     *
     * @param ordersGoods
     * @return
     */
    @Override
    public boolean insertOrderGoods(MyOrdersDto ordersGoods) {
        return orderMapper.insertOrderGoods(ordersGoods);
    }

    @Override
    public void finishOrders(String ordersId) {
        //修改订单的完成标志为true
        orderMapper.updateFinishFlagTrueById(ordersId);

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //从缓存中获取未完成订单的列表
        String unFinishOrdersListStr = ops.get("unFinishOrdersList");
        if(unFinishOrdersListStr != null && !"".equals(unFinishOrdersListStr)){
            Gson gson = new Gson();
            List list = gson.fromJson(unFinishOrdersListStr, List.class);
            for (int i = 0;i < list.size();i++){
                LinkedTreeMap o = (LinkedTreeMap)list.get(i);
                if(o.get("ordersId").equals(ordersId)){
                    list.remove(i);
                    break;
                }
            }
            //写回缓存
            String json = gson.toJson(list);
            ops.set("unFinishOrdersList",json);
        }
    }

}
