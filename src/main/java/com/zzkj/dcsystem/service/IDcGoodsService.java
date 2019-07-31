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
public interface IDcGoodsService {
    /**
     * 查询所有商品
     * @return
     */
    List<DcGoods> selectAllGoods();

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
}
