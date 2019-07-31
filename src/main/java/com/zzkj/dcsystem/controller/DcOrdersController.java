package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.dto.*;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.service.impl.DcOrdersServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:52
 */
@Controller
public class DcOrdersController {

    @Autowired
    private DcOrdersServiceServiceImpl ordersService;
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
    public @ResponseBody List<OrdersDto> selectOrder(@RequestBody DcOrders orders){
        List<OrdersDto> ordersList = ordersService.selectOrderByUserId(orders.getUser().getUserId());
        return ordersList;
    }
}
