package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.dto.ShopCartGoodsDto;
import com.zzkj.dcsystem.entity.DcGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 16:14
 */
public interface IDcShopCartService {

    /**
     * 插入购物车
     * @param shopCartId
     * @param userId
     * @param totalMoney
     */
    boolean insertShopCart(String shopCartId, String userId, Float totalMoney);

    /**
     * 通过用户ID查询该用户是否已经存在购物车,如果有返回true，反之返回false
     * @param userId
     * @return
     */
    boolean selectShopCartByUserId(String userId);
    /**
     * 通过用户ID查询该用户的购物车Id
     * @param userId
     * @return
     */
    String selectShopCartIdByUserId(String userId);

    /**
     * 通过用户ID查询该用户是否已经存在购物车
     * @param shopCartId
     * @param goodsId
     * @param amount
     * @param total
     * @return
     */
    boolean insertGoodsToShopCart(String shopCartId,String goodsId,Integer amount,Float total);

    /**
     * 清空购物车
     * @param shopCartId
     * @return
     */
    boolean emptyShopCart(String shopCartId);

    /**
     * 通过购物车id查询该用户购物车的所有商品
     * @param shopCartId
     * @return
     */
    List<ShopCartGoodsDto> selectAllShopCartGoods(String shopCartId);
}
