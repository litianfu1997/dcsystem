package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcGoods;
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
    @Select("select * from dc_goods")
    List<DcGoods> selectAllGoods();
}
