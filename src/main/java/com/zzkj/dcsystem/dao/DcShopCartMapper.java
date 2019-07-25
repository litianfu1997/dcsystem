package com.zzkj.dcsystem.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 16:02
 */
@Mapper
public interface DcShopCartMapper {

    /**
     * 当用户添加商品到购物车时，购物车便会被创建，只能创建一次
     * @return
     */
    @Insert("insert into dc_shopCart(shopCart_id,user_id,total_money) " +
            "value(#{shopCartId},#{userId},#{totalMoney})")
    boolean insertShopCart(@Param("shopCartId")String shopCartId,@Param("userId")String userId,@Param("totalMoney")Float totalMoney);

    /**
     * 通过用户ID查询该用户是否已经存在购物车
     * @param userId
     * @return
     */
    @Select("select count(*) from dc_shopCart where user_id=#{userId}")
    Integer selectShopCartByUserId(String userId);
    /**
     * 通过用户ID查询用户的购物车Id
     * @param userId
     * @return
     */
    @Select("select shopCart_id from dc_shopCart where user_id=#{userId}")
    String selectShopCartIdByUserId(String userId);
}
