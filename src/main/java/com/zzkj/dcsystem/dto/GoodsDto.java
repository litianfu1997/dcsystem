package com.zzkj.dcsystem.dto;

import com.zzkj.dcsystem.entity.DcGoods;
import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 15:48
 */
@Data
public class GoodsDto {
    private String typeName;
    private String goodsId;
    private String goodsName;
    private Float goodsPrice;
    private String goodsType;
    private Integer goodsTotal;
    private String goodsImgUrl;
}
