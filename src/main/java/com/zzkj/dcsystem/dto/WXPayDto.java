package com.zzkj.dcsystem.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-08 16:29
 */
@Data
public class WXPayDto {
    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String paySign;
}
