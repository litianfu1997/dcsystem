package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:48
 */
public interface IDcOrdersService {
    /**
     * 插入订单
     * @param orders
     * @return
     */
    boolean insertOrder(OrdersDto orders);

    /**
     * 通过userId查询订单
     * @param userId
     * @return
     */
    List<OrdersDto> selectOrderByUserId(String userId);
    /**
     * 插入订单的商品列表
     * @param ordersGoods
     * @return
     */
    boolean insertOrderGoods(MyOrdersDto ordersGoods);

}
