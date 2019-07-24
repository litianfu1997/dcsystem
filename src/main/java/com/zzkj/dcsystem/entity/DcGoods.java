package com.zzkj.dcsystem.entity;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:44
 */
@Data
public class DcGoods {
    private String goodsId;
    private String goodsName;
    private Float goodsPrice;
    private String goodsType;
    private Integer goodsTotal;
    private String goodsDetails;
    private String goodsImgUrl;

}

