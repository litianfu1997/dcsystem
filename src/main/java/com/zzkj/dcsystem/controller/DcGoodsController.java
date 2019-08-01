package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.entity.DcGoodsType;
import com.zzkj.dcsystem.service.DcGoodsTypeService;
import com.zzkj.dcsystem.service.impl.DcGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private DcGoodsTypeService dcGoodsTypeService;

    @RequestMapping("/allGoods.action")
    public @ResponseBody
    Map<String,List<DcGoods>> allGoods(){
        String n = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Map<String, List<DcGoods>> dcGoodsMap = new HashMap<>();
        //获取所有的商品信息
        List<DcGoods> dcGoodsList = goodsService.selectAllGoods();
        //获取所有的类别信息
        List<DcGoodsType> dcGoodsTypeList = dcGoodsTypeService.getAllDcGoodsType();
        //遍历类别集合，给每一个类别创建一个list
        for (int i = 0;i<dcGoodsTypeList.size();i++) {
            dcGoodsMap.put(String.valueOf(n.charAt(i)),new ArrayList<>());
        }

        //遍历商品集合，给商品按类别放入不同的集合当中
        for(int i = 0;i<dcGoodsList.size();i++){
            for (int j = 0;j<dcGoodsTypeList.size();j++){
                //如果该商品属于该类别，则放入对应的list集合中
                if(dcGoodsList.get(i).getGoodsType().getTypeId().
                        equals(dcGoodsTypeList.get(j).getTypeId())){
                    dcGoodsMap.get(String.valueOf(n.charAt(i))).add(dcGoodsList.get(i));
                }
            }
        }
//        List<DcGoods> HXGoods = new ArrayList<>();
//        List<DcGoods> SCGoods = new ArrayList<>();
//        for (DcGoods dcGood : dcGoods) {
//            if ("海鲜".equals(dcGood.getGoodsType().getTypeName())){
//                HXGoods.add(dcGood);
//                dcGoodsMap.put("A",HXGoods);
//            }else if("蔬菜".equals(dcGood.getGoodsType().getTypeName())){
//                SCGoods.add(dcGood);
//                dcGoodsMap.put("B",SCGoods);
//            }
//        }

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

    /**
     * 根据goodsId删除商品
     * @param dcGoods
     * @return
     */
    @RequestMapping(value = "deleteGoods")
    public @ResponseBody List<DcGoods> deleteGoods(DcGoods dcGoods){
        //删除商品
        goodsService.deleteGoodsByGoodsId(dcGoods);
        //重定向到商品列表
        return this.goodsList();
    }

    @RequestMapping(value = "/addGoods")
    public  String addGoods(HttpServletRequest request,DcGoods dcGoods, @RequestParam(value = "goodsImg") MultipartFile goodsImg){
        //生成新文件名文件名(包含后缀)
        String imgName = UUID.randomUUID().toString().replace("-","")+goodsImg.getOriginalFilename();
        //获取项目路径
        String contextPath = request.getContextPath();
        //指定文件存放在d盘下
        File file = new File("D:/file/"+imgName);
        try {
            goodsImg.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(dcGoods);
        dcGoods.setGoodsImgUrl(contextPath+"/images/"+imgName);
        //插入数据
        goodsService.addDcGoods(dcGoods);

        return "redirect:/toDishes";
    }

}
