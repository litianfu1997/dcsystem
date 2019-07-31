package com.zzkj.dcsystem.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:47
 */
@Data
public class DcOrders {
    private String ordersId;
    private DcUser user;
    private String linkman;
    private String phone;
    private Date createDate;
    private String note;
    private String storeAddress;
    private String userAddress;
    /**
     * 订单的总价格
     */
    private Float combined;
    /**
     * 评价
     */
    private String evaluation;
    /**
     * 订单中的商品信息列表
     */
    private List<DcOrdersGoods> goodsList;
}