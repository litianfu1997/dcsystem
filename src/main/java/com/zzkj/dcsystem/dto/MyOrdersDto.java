package com.zzkj.dcsystem.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 17:17
 */
@Data
public class MyOrdersDto {
    private String ordersId;
    private String goodsId;
    private Integer amount;
    private Float total;
}
