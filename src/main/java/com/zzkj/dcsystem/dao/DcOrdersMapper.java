package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.controller.utils.DcOrdersQueryVo;
import com.zzkj.dcsystem.dto.MyOrdersDto;
import com.zzkj.dcsystem.dto.OrdersDto;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcUser;
import org.apache.ibatis.annotations.*;

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
