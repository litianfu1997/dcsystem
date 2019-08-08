package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 14:39
 */
public interface DcGoodsService {
    /**
     * 查询所有商品
     * @return
     */
    List<DcGoods> selectAllGoods();

    /**
     * 通过goodId查询商品信息
     * @param goodsId
     * @return
     */
    DcGoods selectGoodsById(String goodsId);

    /**
     * 根据条件来查询商品
     * @param queryVo
     * @return
     */
    List<DcGoods> selectGoods(DcGoodsQueryVo queryVo);

    /**
     * 根据id删除商品
     * @param dcGoods
     */
    void deleteGoodsByGoodsId(DcGoods dcGoods);

    /**
     * 插入商品信息
     * @param dcGoods
     */
    void addDcGoods(DcGoods dcGoods);

    /**
     * 通过goodId查询商品信息,封装有类别信息
     * @param goodsId
     * @return
     */
    DcGoods getGoodsById(String goodsId);

    /**
     *
     * 查询所有的商品信息,封装有类别信息
     * @return
     */
    List<DcGoods> getAllGoods();

    /**
     * 修改数据
     * @param dcGoods
     */
    void updateGoods(DcGoods dcGoods);
}
