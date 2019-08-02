package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.controller.utils.DcOrdersQueryVo;
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
     * 通过订单id获未完成订单的所有信息
     * @param ordersId
     * @return
     */
    DcOrders selectUnDcOrderByOrderId(String ordersId);

    /**
     * 获取所有订单信息
     * @return
     */
    List<DcOrders> getAllOrder();

    /**
     * 条件查询
     * @param queryVo
     * @return
     */
    List<DcOrders> selectOrders(DcOrdersQueryVo queryVo);
}
