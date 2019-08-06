package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.controller.utils.DcGoodsQueryVo;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.entity.DcGoodsType;
import com.zzkj.dcsystem.service.DcGoodsTypeService;
import com.zzkj.dcsystem.service.impl.DcGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Value("${file.upload.path}")
    private String path = "file/";
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
                    dcGoodsMap.get(String.valueOf(n.charAt(j))).add(dcGoodsList.get(i));
                }
            }
        }
//        List<DcGoods> HXGoods = new ArrayList<>();
//        List<DcGoods> SCGoods = new ArrayList<>();
//        for (DcGoods dcGood : dcGoodsList) {
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
        List<DcGoods> dcGoods = goodsService.getAllGoods();
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

    /**
     * 插入商品
     * @param request
     * @param dcGoods
     * @param goodsImg
     * @return
     */
    @RequestMapping(value = "/addGoods")
    public  String addGoods(HttpServletRequest request,DcGoods dcGoods, @RequestParam(value = "goodsImg") MultipartFile goodsImg){
        if (!goodsImg.isEmpty()){
            //生成新文件名文件名(包含后缀)
            String imgName = UUID.randomUUID().toString().replace("-","")+goodsImg.getOriginalFilename();
            //获取项目路径
            String contextPath = request.getContextPath();
            //指定文件存放在d盘下
            File file = new File(new File(path).getAbsolutePath()+ "/" +imgName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                System.out.println("文件已上传");
                goodsImg.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        System.out.println(dcGoods);
            dcGoods.setGoodsImgUrl(contextPath+"/images/"+imgName);
        }
        //插入数据
        goodsService.addDcGoods(dcGoods);

        return "redirect:/toDishes";
    }

    /**
     * 修改商前的准备
     * @param dcGoods
     * @param model
     * @return
     */
    @RequestMapping(value = "/preUpdateGoods")
    public String preUpdateGoods(DcGoods dcGoods, Model model){
        //根据商品id查询商品信息
        DcGoods goods = goodsService.getGoodsById(dcGoods.getGoodsId());
        //查询所有类别信息
        List<DcGoodsType> allDcGoodsType = dcGoodsTypeService.getAllDcGoodsType();
        //放入数据域
        model.addAttribute("goods",goods);
        model.addAttribute("goodsType",allDcGoodsType);
        return "update";
    }

    @RequestMapping(value = "/updateGoods")
    public String updateGoods(HttpServletRequest request,DcGoods dcGoods, @RequestParam(value = "goodsImg") MultipartFile goodsImg){

        if (!goodsImg.isEmpty()){
            //生成新文件名文件名(包含后缀)
            String imgName = UUID.randomUUID().toString().replace("-","")+goodsImg.getOriginalFilename();
            //获取项目路径
            String contextPath = request.getContextPath();
            //指定文件存放在d盘下
            File file = new File(new File(path).getAbsolutePath()+ "/" +imgName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                System.out.println("文件已上传");
                goodsImg.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dcGoods.setGoodsImgUrl(contextPath+"/images/"+imgName);
        }
        //修改数据
        goodsService.updateGoods(dcGoods);

        return "redirect:/toDishes";
    }

}
