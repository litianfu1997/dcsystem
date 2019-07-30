package com.zzkj.dcsystem.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-25 10:58
 */
@Data
public class ShopCartGoodsDto {
    private String shopCartId;
    private String goodsId;
    private String goodsName;
    private Float goodsPrice;
    /**
     * 商品总数，剩余量
     */
    private Integer goodsTotal;
    /**
     * 商品总价
     */
    private Float total;
    /**
     * 商品总数量
     */
    private Integer amount;
    private String goodsImgUrl;
}
