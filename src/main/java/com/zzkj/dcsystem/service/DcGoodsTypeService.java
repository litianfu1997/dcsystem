package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.entity.DcGoodsType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JGZ
 * @Classname DcGoodsTypeService
 * @Date 2019/7/30 10:41
 * @Email 1945282561@qq.com
 */
public interface DcGoodsTypeService {

    /**
     * 获取所有的商品类别
     * @return
     */
    public List<DcGoodsType> getAllDcGoodsType();

    /**
     * 添加类别类别
     * @param dcGoodsType
     * @return
     */
    Boolean addDcGoodsType(DcGoodsType dcGoodsType);

    /**
     * 通过id删除类别
     * @param dcGoodsType
     */
    void deleteTypeById(DcGoodsType dcGoodsType);
}
