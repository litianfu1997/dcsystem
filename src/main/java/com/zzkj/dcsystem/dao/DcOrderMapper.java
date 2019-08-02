package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:32
 */
@Mapper
public interface DcOrderMapper {
    /**
     * 插入订单
     * @param orders
     * @return
     */
    @Insert("insert into dc_orders(orders_id,linkman,phone,create_date,note,store_address,user_address,combined,evaluation,user_id)" +
            "value(#{ordersId},#{linkMan},#{phone},#{createDate},#{note},#{storeAddress},#{userAddress},#{combined},#{evaluation},#{userId})")
    boolean insertOrder(OrdersDto orders);

    /**
     * 通过userId查询订单
     * @param userId
     * @return
     */
    @Select("select * from dc_orders where user_id=#{userId}")
    List<OrdersDto> selectOrderByUserId(@Param("userId")String userId);

    /**
     * 插入订单的商品列表
     * @param ordersGoods
     * @return
     */
    @Insert("insert into dc_orders_goods(orders_id,goods_id,amount,total) value(#{ordersId},#{goodsId},#{amount},#{total})")
    boolean insertOrderGoods(MyOrdersDto ordersGoods);

    /**
     * 通过id将订单修改为已完成i
     * @param ordersId
     */
    @Update("update dc_orders set finish_flag=1 where orders_id=#{ordersId}")
    void updateFinishFlagTrueById(@Param("ordersId")String ordersId);

}
