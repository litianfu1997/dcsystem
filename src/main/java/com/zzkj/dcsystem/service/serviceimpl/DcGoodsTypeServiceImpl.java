package com.zzkj.dcsystem.service.serviceimpl;

import com.zzkj.dcsystem.dao.DcGoodsTypeMapper;
import com.zzkj.dcsystem.entity.DcGoodsType;
import com.zzkj.dcsystem.service.DcGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * @Classname DcGoodsTypeServiceImpl
 * @Date 2019/7/30 10:42
 * @Email 1945282561@qq.com
 */
@Service
public class DcGoodsTypeServiceImpl implements DcGoodsTypeService {

    @Autowired
   private DcGoodsTypeMapper dcGoodsTypeMapper;

    @Override
    public List<DcGoodsType> getAllDcGoodsType() {
        List<DcGoodsType> dcGoodsTypes = dcGoodsTypeMapper.selectAllDcGoodsType();
        for (DcGoodsType dcGoodsType:dcGoodsTypes) {
            Integer integer = dcGoodsTypeMapper.selectGoodsNumberByType(dcGoodsType);
            dcGoodsType.setGoodsNumber(integer);
        }
        return dcGoodsTypes;
    }

    @Override
    public Boolean addDcGoodsType(DcGoodsType dcGoodsType) {
        //查询数据库中是否存在此类别
        List<DcGoodsType> dcGoodsTypes =dcGoodsTypeMapper.selectDcGoodTypeByTypeName(dcGoodsType);
        if(dcGoodsTypes.size() == 0){
            //如果不存在
            dcGoodsType.setTypeId(UUID.randomUUID().toString());
            //插入类别
            dcGoodsTypeMapper.insertDcGoodType(dcGoodsType);
            return true;
        }
        else {
            //如果存在不插入
            return false;
        }

    }

    @Override
    public void deleteTypeById(DcGoodsType dcGoodsType) {
        dcGoodsTypeMapper.deleteTypeById(dcGoodsType);
    }
}
