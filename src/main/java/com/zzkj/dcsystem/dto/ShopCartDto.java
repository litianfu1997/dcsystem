package com.zzkj.dcsystem.dto;

import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.entity.DcUser;
import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 15:54
 */
@Data
public class ShopCartDto {
    private String userId;
    private String goodsId;
    private Integer amount;
    private Float total;

}
