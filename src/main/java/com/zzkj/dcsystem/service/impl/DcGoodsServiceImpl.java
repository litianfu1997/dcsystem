package com.zzkj.dcsystem.service.impl;

import com.zzkj.dcsystem.dao.DcGoodsMapper;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.service.IDcGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 14:35
 */
@Service
public class DcGoodsServiceImpl implements IDcGoodsService {
    @Autowired
    private DcGoodsMapper goodsMapper;

    /**
     * 查询所有商品
     *
     * @return
     */
    @Override
    public List<DcGoods> selectAllGoods() {
        List<DcGoods> dcGoods = goodsMapper.selectAllGoods();
        return dcGoods;
    }


}