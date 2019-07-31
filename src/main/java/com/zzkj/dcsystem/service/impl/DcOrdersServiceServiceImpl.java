package com.zzkj.dcsystem.service.impl;

import com.zzkj.dcsystem.dao.DcOrderMapper;
import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.service.IDcOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
