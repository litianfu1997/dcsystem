package com.zzkj.dcsystem.entity;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:59
 */
@Data
public class DcShopCart {
    private String shopCartId;
    private String userID;
    private float totalMoney;
}
