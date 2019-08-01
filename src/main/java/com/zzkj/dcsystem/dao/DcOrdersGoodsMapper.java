package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 查询订单商品表dao
 * @author JGZ
 * @Classname DcOrdersGoodsMapper
 * @Date 2019/7/24 16:47
 * @Email 1945282561@qq.com
 */
@Mapper
public interface DcOrdersGoodsMapper {

    /**
     * 通过订单查询订单中的商品
     * @param dcOrders
     * @return 订单中的商品列表
     */
    public List<DcOrdersGoods> selectDcOrdersGoodsByDcOrders(DcOrders dcOrders);


}
