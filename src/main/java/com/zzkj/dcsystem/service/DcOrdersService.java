package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.controller.utils.DcOrdersQueryVo;
import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;

import java.util.List;

/**
 * @author JGZ
 * @Classname DcOrdersService
 * @Date 2019/7/24 16:26
 * @Email 1945282561@qq.com
 */
public interface DcOrdersService {

    /**
     * 通过用户id获取用户订单列表
     * @param userId 用户id
     * @return
     */
    public List<DcOrders> getDcOrdersListByUserId(String userId);

    /**
     * 通过订单id获订单的所有信，包括订单中的商品
     * @param ordersId
     * @return
     */
    public DcOrders selectDcOrderByOrderId(String ordersId);

    /**
     * 获取所有订单
     * @return
     */
    List<DcOrders> getAllOrder();

    /**
     * 根据条件查询订单
     * @param queryVo
     * @return
     */
    List<DcOrders> selectOrders(DcOrdersQueryVo queryVo);

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

    /**
     * 完成订单
     * @param ordersId
     */
    void finishOrders(String ordersId);


}
