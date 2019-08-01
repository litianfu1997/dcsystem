package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * @Classname DcOrdersMapper
 * @Date 2019/7/24 16:36
 * @Email 1945282561@qq.com
 */
@Mapper
public interface DcOrdersMapper {

    /**
     * 通过userId查询用户的订单
     * @param user
     * @return
     */
    public List<DcOrders> selectOrdersByUserId(DcUser user);

    /**
     * 通过订单id获订单的所有信息，包括订单中的商品
     * @param ordersId
     * @return
     */
    DcOrders selectDcOrderByOrderId(String ordersId);
}
