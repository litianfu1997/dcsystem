package com.zzkj.dcsystem.dto;

import com.zzkj.dcsystem.entity.DcUser;
import lombok.Data;

import java.util.Date;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 16:00
 */
@Data
public class OrdersDto {
    private String ordersId;
    private String linkMan;
    private String phone;
    private Date createDate;
    private String note;
    private String storeAddress;
    private String userAddress;
    private String userId;
    /**
     * 订单的总价格
     */
    private Float combined;
    /**
     * 评价
     */
    private String evaluation;
}
