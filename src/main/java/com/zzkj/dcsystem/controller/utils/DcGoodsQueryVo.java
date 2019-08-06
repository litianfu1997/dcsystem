package com.zzkj.dcsystem.controller.utils;

import lombok.Data;

/**
 * 商品查询工具
 * @author JGZ
 * @Classname QueryVo
 * @Date 2019/7/30 16:59
 * @Email 1945282561@qq.com
 */
@Data
public class DcGoodsQueryVo {
    private String goodsName;
    private String typeId;
    private Float startPrice;
    private Float endPrice;
}
