package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.entity.DcOrders;

import java.util.List;

/**
 * @author JGZ
 * @Classname DcOrdersService
 * @Date 2019/7/24 16:26
 * @Email 1945282561@qq.com
 */
public interface DcOrdersService {

    /**
     * 通过用户id获取用户订单列表
     * @param userId 用户id
     * @return
     */
    public List<DcOrders> getDcOrdersListByUserId(String userId);
}
