package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.service.impl.DcGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            if ("海鲜".equals(dcGood.getGoodsType().getTypeName())){
                HXGoods.add(dcGood);
                dcGoodsMap.put("A",HXGoods);
            }else if("蔬菜".equals(dcGood.getGoodsType().getTypeName())){
                SCGoods.add(dcGood);
                dcGoodsMap.put("B",SCGoods);
            }
        }

        return dcGoodsMap;
    }

    /**
     * 获取所有菜品
     * @return
     */
    @RequestMapping(value = "/goodsList")
    public @ResponseBody List<DcGoods> goodsList(){
        List<DcGoods> dcGoods = goodsService.selectAllGoods();
        return dcGoods;
    }

    /**
     * 条件查询商品
     * @param queryVo
     * @return
     */
    @RequestMapping(value = "/selectGoods")
    public @ResponseBody List<DcGoods> selectGoods(DcGoodsQueryVo queryVo){
        List<DcGoods> dcGoods = goodsService.selectGoods(queryVo);
        return dcGoods;
    }
    /**
     * 条件查询商品
     * @param queryVo
     * @return
     */
    @RequestMapping(value = "/WXselectGoods")
    public @ResponseBody List<DcGoods> WXselectGoods(@RequestBody DcGoodsQueryVo queryVo){
        List<DcGoods> dcGoods = goodsService.selectGoods(queryVo);
        return dcGoods;
    }

    @RequestMapping(value = "deleteGoods")
    public String deleteGoods(DcGoods dcGoods){
        //删除商品
        goodsService.deleteGoodsByGoodsId(dcGoods);
        //重定向到商品列表
        return "redirect:/goodsList";
    }

}
