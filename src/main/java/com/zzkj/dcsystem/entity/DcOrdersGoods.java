package com.zzkj.dcsystem.entity;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 11:07
 */
@Data
public class DcOrdersGoods {
    private String ordersId;
    private String goodsId;
    private int amount;
    private float total;
}
