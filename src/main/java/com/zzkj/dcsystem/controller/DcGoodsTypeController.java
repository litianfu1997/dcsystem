package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.entity.DcGoodsType;
import com.zzkj.dcsystem.service.DcGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品类别操作
 * @author JGZ
 * @Classname DcGoodsTypeController
 * @Date 2019/7/30 10:39
 * @Email 1945282561@qq.com
 */
@Controller
public class DcGoodsTypeController {

    @Autowired
    private DcGoodsTypeService dcGoodsTypeService;

    /**
     * 获得所有的商品类别
     * @return
     */
    @RequestMapping(value = "/typeList")
    public @ResponseBody
    List<DcGoodsType>  typeList(){
        List<DcGoodsType> allDcGoodsType = dcGoodsTypeService.getAllDcGoodsType();
        return allDcGoodsType;
    }

}
