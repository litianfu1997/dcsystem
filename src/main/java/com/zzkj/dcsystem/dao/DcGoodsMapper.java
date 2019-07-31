package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 14:35
 */
@Mapper
public interface DcGoodsMapper {
    /**
     * 查询所有商品信息
     * @return
     */
    @Select("select * from dc_goods")
    List<DcGoods> selectAllGoods();

    /**
     * 根据条件查询商品信息
     * @param queryVo
     * @return
     */
    List<DcGoods> selectGoods(DcGoodsQueryVo queryVo);

    /**
     * 根据goodsId删除商品
     * @param dcGoods
     */
    @Delete("delete from dc_goods where goods_id = #{goodsId}")
    void deleteGoodsByGoodsId(DcGoods dcGoods);
}
