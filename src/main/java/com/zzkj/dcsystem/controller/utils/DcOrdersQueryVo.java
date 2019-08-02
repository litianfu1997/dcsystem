package com.zzkj.dcsystem.controller.utils;

import lombok.Data;

/**
 * @author JGZ
 * @Classname DcOrdersQueryVo
 * @Date 2019/8/2 16:13
 * @Email 1945282561@qq.com
 */
@Data
public class DcOrdersQueryVo {
    private String linkman;
    private String phone;
    private String startDate;
    private String endDate;
    private Boolean finishFlag;
}
