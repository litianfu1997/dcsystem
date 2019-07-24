package com.zzkj.dcsystem.service;

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
}
