package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.entity.DcGoodsType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    @Select("select * from dc_goods,dc_goods_type where dc_goods.type_id=dc_goods_type.type_id")
    List<DcGoods> selectAllGoods();

    /**
     * 通过goodId查询商品信息
     * @param goodsId
     * @return
     */
    @Select("select * from dc_goods where goods_id = #{goodsId}")
    DcGoods selectGoodsById(@Param("goodsId")String goodsId);
    @Select("select * from dc_goods,dc_goods_type where dc_goods.type_id=dc_goods_type.type_id")
    List<DcGoodsType> selectGoodsType();
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
