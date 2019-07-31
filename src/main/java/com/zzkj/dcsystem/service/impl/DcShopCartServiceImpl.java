package com.zzkj.dcsystem.service.impl;

import com.zzkj.dcsystem.dao.DcGoodsMapper;
import com.zzkj.dcsystem.dao.DcShopCartGoodsMapper;
import com.zzkj.dcsystem.dao.DcShopCartMapper;
import com.zzkj.dcsystem.dto.ShopCartGoodsDto;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.service.IDcShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 16:19
 */
@Service
public class DcShopCartServiceImpl implements IDcShopCartService {
    @Autowired
    private DcGoodsMapper goodsMapper;
    @Autowired
    private DcShopCartMapper shopCartMapper;
    @Autowired
    private DcShopCartGoodsMapper shopCartGoodsMapper;
    /**
     * 插入购物车
     *
     * @param shopCartId
     * @param userId
     * @param totalMoney
     */
    @Override
    public boolean insertShopCart(String shopCartId, String userId, Float totalMoney) {
            shopCartMapper.insertShopCart(shopCartId,userId,totalMoney);
            return true;
    }

    /**
     * 通过用户ID查询该用户是否已经存在购物车,如果有返回true，反之返回false
     *
     * @param userId
     * @return
     */
    @Override
    public boolean selectShopCartByUserId(String userId) {
        Integer shopCartNum = shopCartMapper.selectShopCartByUserId(userId);
        if (shopCartNum<1){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 通过用户ID查询该用户的购物车Id
     *
     * @param userId
     * @return
     */
    @Override
    public String selectShopCartIdByUserId(String userId) {
        String shopCartId = shopCartMapper.selectShopCartIdByUserId(userId);
        return shopCartId;
    }

    /**
     * 通过用户ID查询该用户是否已经存在购物车
     * @param shopCartId
     * @param goodsId
     * @param amount
     * @param total
     * @return
     */
    @Override
    public boolean insertGoodsToShopCart(String shopCartId, String goodsId, Integer amount, Float total) {
        boolean insertFlag = shopCartGoodsMapper.insertGoodsToShopCart(shopCartId, goodsId, amount, total);
        return insertFlag;
    }

    /**
     * 清空购物车
     * @param shopCartId
     * @return
     */
    @Override
    public boolean emptyShopCart(String shopCartId) {
        boolean emptyFlag = shopCartGoodsMapper.emptyShopCart(shopCartId);
        return emptyFlag;
    }

    /**
     * 通过购物车id查询该用户购物车的所有商品
     *
     * @param shopCartId
     * @return
     */
    @Override
    public List<ShopCartGoodsDto> selectAllShopCartGoods(String shopCartId) {
        List<ShopCartGoodsDto> dcGoodsList = shopCartGoodsMapper.selectAllShopCartGoods(shopCartId);

        return dcGoodsList;
    }

    /**
     * 修改购物车商品数量及总价
     * @param shopCartId
     * @param goodsId
     * @param amount
     * @param total
     * @return
     */
    @Override
    public boolean updateShopCartGoodsCount(String shopCartId, String goodsId, Integer amount, Float total) {
        boolean updateFlag = shopCartGoodsMapper.updateShopCartGoodsCount(shopCartId, goodsId, amount, total);
        return updateFlag;
    }

    /**
     * 查询该用户的购物车是否存在相同商品
     * @param shopCartId
     * @param goodsId
     * @return
     */
    @Override
    public boolean selectShopCartGoodsByGoodsId(String shopCartId, String goodsId) {
        Integer existFlag = shopCartGoodsMapper.selectShopCartGoodsByGoodsId(shopCartId, goodsId);
        if (existFlag == null){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 通过shopCartId和goodsId删除购物车里的某一款商品
     *
     * @param shopCartId
     * @param goodsId
     * @return
     */
    @Override
    public boolean deleteShopCartGoodsById(String shopCartId, String goodsId) {
        boolean deleteFlag = shopCartGoodsMapper.deleteShopCartGoodsById(shopCartId, goodsId);
        return deleteFlag;
    }

}
