package com.zzkj.dcsystem.dao;

import com.zzkj.dcsystem.entity.DcGoodsType;
import org.apache.ibatis.annotations.*;

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

    /**
     * 更具类别id查询商品的数量
     * @param goodsType
     * @return
     */
    @Select("select count(*) from dc_goods where type_id= #{typeId}")
    public Integer selectGoodsNumberByType(DcGoodsType goodsType);

    /**
     * 更具类别名查询类别
     * @param goodsType
     * @return
     */
    @Select("select * from dc_goods_type where type_name= #{typeName}")
    public List<DcGoodsType> selectDcGoodTypeByTypeName(DcGoodsType goodsType);

    /**
     * 插入类别信息
     * @param dcGoodsType
     */
    @Insert("insert into dc_goods_type(type_id,type_name) values(#{typeId},#{typeName})")
    void insertDcGoodType(DcGoodsType dcGoodsType);

    /**
     * 通过id删除类别
     * @param dcGoodsType
     */
    @Delete("delete  from dc_goods_type where type_id = #{typeId}")
    void deleteTypeById(DcGoodsType dcGoodsType);
}
