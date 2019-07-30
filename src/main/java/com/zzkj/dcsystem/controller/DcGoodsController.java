package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.service.impl.DcGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 14:26
 */
@Controller
public class DcGoodsController {
    @Autowired
    private DcGoodsServiceImpl goodsService;
    @RequestMapping("/allGoods.action")
    public @ResponseBody
    Map<String,List<DcGoods>> allGoods(){
        Map<String, List<DcGoods>> dcGoodsMap = new HashMap<>();
        List<DcGoods> dcGoods = goodsService.selectAllGoods();
        List<DcGoods> HXGoods = new ArrayList<>();
        List<DcGoods> SCGoods = new ArrayList<>();
        for (DcGoods dcGood : dcGoods) {
            if ("海鲜".equals(dcGood.getDcGoodsType().getTypeName())){
                HXGoods.add(dcGood);
                dcGoodsMap.put("A",HXGoods);
            }else if("蔬菜".equals(dcGood.getDcGoodsType().getTypeName())){
                SCGoods.add(dcGood);
                dcGoodsMap.put("B",SCGoods);
            }
        }

        return dcGoodsMap;
    }

}
