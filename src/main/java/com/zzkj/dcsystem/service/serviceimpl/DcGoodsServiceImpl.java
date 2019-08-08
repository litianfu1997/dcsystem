package com.zzkj.dcsystem.service.serviceimpl;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.dao.DcGoodsMapper;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.entity.DcGoodsType;
import com.zzkj.dcsystem.service.DcGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 14:35
 */
@Service
public class DcGoodsServiceImpl implements DcGoodsService {
    @Autowired
    private DcGoodsMapper goodsMapper;

    /**
     * 查询所有商品
     *
     * @return
     */
    @Override
    public List<DcGoods> selectAllGoods() {
        List<DcGoodsType> goodsTypes=goodsMapper.selectGoodsType();
        List<DcGoods> dcGoods = goodsMapper.selectAllGoods();
        for (DcGoodsType goodsType : goodsTypes) {
            for (DcGoods dcGood : dcGoods) {
                if (goodsType.getGoodsId().equals(dcGood.getGoodsId())){
                    dcGood.setGoodsType(goodsType);
                }

            }
        }
        return dcGoods;
    }

    /**
     * 通过goodId查询商品信息
     * @param goodsId
     * @return
     */
    @Override
    public DcGoods selectGoodsById(String goodsId) {
        return goodsMapper.selectGoodsById(goodsId);
    }
    /**
     * 根据条件来查询商品
     * @param queryVo
     * @return
     */
    @Override
    public List<DcGoods> selectGoods(DcGoodsQueryVo queryVo) {
        queryVo.setGoodsName("%"+queryVo.getGoodsName()+"%");
        List<DcGoods> dcGoods = goodsMapper.selectGoods(queryVo);
        return dcGoods;
    }

    @Override
    public void deleteGoodsByGoodsId(DcGoods dcGoods) {
        goodsMapper.deleteGoodsByGoodsId(dcGoods);
    }

    @Override
    public void addDcGoods(DcGoods dcGoods) {
        dcGoods.setGoodsId(UUID.randomUUID().toString());
        goodsMapper.addDcGoods(dcGoods);
    }

    @Override
    public DcGoods getGoodsById(String goodsId) {
        DcGoods goods = goodsMapper.getGoodsById(goodsId);
        return goods;
    }

    @Override
    public List<DcGoods> getAllGoods() {
        List<DcGoods> goods = goodsMapper.getAllGoods();
        return goods;
    }

    @Override
    public void updateGoods(DcGoods dcGoods) {
        goodsMapper.updateGoods(dcGoods);
    }


}
