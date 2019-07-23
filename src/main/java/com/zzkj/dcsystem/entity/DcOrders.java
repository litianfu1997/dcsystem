package com.zzkj.dcsystem.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:47
 */
@Data
public class DcOrders {
    private String ordersId;
    private String linkMan;
    private String phone;
    private Date createDate;
    private String note;
    private String storeAddress;
    private String userAddress;
    /**
     * 订单的总价格
     */
    private float combined;
    /**
     * 评价
     */
    private String evaluation;
}