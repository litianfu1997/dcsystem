package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.dto.ShopCartGoodsDto;
import com.zzkj.dcsystem.entity.DcGoods;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 16:56
 */
@Mapper
public interface DcShopCartGoodsMapper {

    /**
     * 插入商品到购物车
     * @param shopCartId
     * @param goodsId
     * @param amount 商品数量
     * @param total  商品总价
     * @return boolean
     */
    @Insert("insert into dc_shopCart_goods(shopCart_id,goods_id,amount,total) value(#{shopCartId},#{goodsId},#{amount},#{total})")
    boolean insertGoodsToShopCart(@Param("shopCartId")String shopCartId, @Param("goodsId")String goodsId,
                               @Param("amount")Integer amount,@Param("total")Float total);

    /**
     * 清空购物车
     * @param shopCartId
     * @return
     */
    @Delete("delete from dc_shopCart_goods where shopCart_id=#{shopCartId}")
    boolean emptyShopCart(@Param("shopCartId")String shopCartId);

    /**
     * 通过购物车id查询该用户购物车的所有商品
     * @param shopCartId
     * @return
     */
    @Select("select dc_shopCart_goods.shopCart_id,dc_goods.goods_name,dc_goods.goods_price,dc_goods.goods_total\n" +
            ",dc_shopCart_goods.total,dc_shopCart_goods.amount,dc_goods.goods_img_url\n" +
            "from dc_shopCart_goods,dc_goods,dc_shopCart,dc_user\n" +
            "where dc_shopCart.shopCart_id=dc_shopCart_goods.shopCart_id\n" +
            "and  dc_user.user_id = dc_shopCart.user_id\n" +
            "and dc_shopCart_goods.goods_id =dc_goods.goods_id\n" +
            "and dc_shopCart_goods.shopCart_id=#{shopCartId}")
    List<ShopCartGoodsDto> selectAllShopCartGoods(@Param("shopCartId")String shopCartId);
}
