package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcGoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author JGZ
 * @Classname DcGoodsTypeMapper
 * @Date 2019/7/30 10:43
 * @Email 1945282561@qq.com
 */
@Mapper
public interface DcGoodsTypeMapper {
    /**
     * 查询所有类别
     * @return
     */
    @Select("select * from dc_goods_type")
    public List<DcGoodsType> selectAllDcGoodsType();

    @Select("select count(*) from dc_goods where type_id= #{typeId}")
    public Integer selectGoodsNumberByType(DcGoodsType goodsType);
}
