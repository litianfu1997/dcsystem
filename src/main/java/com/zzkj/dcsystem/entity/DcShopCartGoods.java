package com.zzkj.dcsystem.entity;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 11:04
 */
@Data
public class DcShopCartGoods {
    private DcShopCart shopCart;
    private DcGoods goods;
    private Integer amount;
    private Float total;
}
