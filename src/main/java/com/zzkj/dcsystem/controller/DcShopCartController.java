package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.dto.Message;
import com.zzkj.dcsystem.dto.ShopCartDto;
import com.zzkj.dcsystem.dto.ShopCartGoodsDto;
import com.zzkj.dcsystem.entity.DcGoods;
import com.zzkj.dcsystem.service.impl.DcGoodsServiceImpl;
import com.zzkj.dcsystem.service.impl.DcShopCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-24 15:50
 */
@Controller
public class DcShopCartController {

    @Autowired
    private DcShopCartServiceImpl shopCartService;
    @Autowired
    private DcGoodsServiceImpl goodsService;

    /**
     * 创建购物车、将商品添加到购物车
     * @param shopCartDto
     */
    @RequestMapping("/shopCart/shopCartOption.action")
    public @ResponseBody
    Message shopCartOption(@RequestBody ShopCartDto shopCartDto){
        //查询该用户是否存在购物车
        boolean flag  = shopCartService.selectShopCartByUserId(shopCartDto.getUserId());
        //如果购物车表中没有该用户的购物车，就添加一辆购物车
        if (flag==false){
            //使用UUID生成100位的唯一ID
            String shopCartId = UUID.randomUUID().toString();
            //给该用户添加一辆购物车
            shopCartService.insertShopCart(shopCartId,shopCartDto.getUserId(),shopCartDto.getTotal());
        }else {//如果该用户已存在一辆购物车，则直接添加商品到购物车
            //通过该用户的Id查询他的购物车Id
            String shopCartId = shopCartService.selectShopCartIdByUserId(shopCartDto.getUserId());
           //查询该商品是否已存在该用户的购物车
            boolean exitsFlag = shopCartService.selectShopCartGoodsByGoodsId(shopCartId, shopCartDto.getGoodsId());
            //判断是否有商品数据和商品是否存在
            if (shopCartDto.getGoodsId()!=null&&!exitsFlag){
                //将商品添加到购物车商品表
                boolean insertFlag = shopCartService.insertGoodsToShopCart(shopCartId, shopCartDto.getGoodsId(), shopCartDto.getAmount(), shopCartDto.getTotal());
                if (insertFlag==false){
                    System.out.println("插入失败");
                    return new Message("error","添加失败");
                }else {
                    System.out.println("插入成功");
                    return new Message("success","添加成功");
                }
            }else {
                System.out.println("该商品已存在");
                return new Message("error","该商品已存在");
            }

        }
        return null;
    }
    @RequestMapping("/shopCart/selectAllShopCartGoods.action")
    public @ResponseBody
    List<ShopCartGoodsDto> selectAllShopCartGoods(@RequestBody ShopCartDto shopCartDto){
        //通过该用户的Id查询他的购物车Id
        String shopCartId = shopCartService.selectShopCartIdByUserId(shopCartDto.getUserId());
        List<ShopCartGoodsDto> ShopCartGoodsList = shopCartService.selectAllShopCartGoods(shopCartId);
        if (ShopCartGoodsList !=null){
            return ShopCartGoodsList;
        }else {
            System.out.println("数据为空");
        }
        return null;
    }

    /**
     * 清空购物车
     * @param shopCartDto
     */
    @RequestMapping("/shopCart/emptyShopCart.action")
    public void emptyShopCart(@RequestBody ShopCartDto shopCartDto){
        //通过该用户的Id查询他的购物车Id
        String shopCartId = shopCartService.selectShopCartIdByUserId(shopCartDto.getUserId());
        boolean emptyFlag = shopCartService.emptyShopCart(shopCartId);
        if (emptyFlag==true){
            //购物车清空成功，执行相应操作操作
            System.out.println("购物车清空成功");
        }else {
            //购物车清空失败，，执行相应操作操作
            System.out.println("购物车清空失败");
        }
    }

    /**
     * 商品数量及总价更新
     * @param shopCartDto
     */
    @RequestMapping("/shopCart/updateShopCartGoods.action")
    public @ResponseBody void updateShopCartGoods(@RequestBody ShopCartDto shopCartDto){
        Integer amount = shopCartDto.getAmount();
        Float total=shopCartDto.getTotal();
        String shopCartId = shopCartService.selectShopCartIdByUserId(shopCartDto.getUserId());
        boolean updateFlag = shopCartService.updateShopCartGoodsCount(shopCartId, shopCartDto.getGoodsId(), amount, total);
    }
    @RequestMapping("/shopCart/deleteShopCartGoodsById.action")
    public @ResponseBody void deleteShopCartGoodsById(@RequestBody ShopCartDto shopCartDto){
        String shopCartId = shopCartService.selectShopCartIdByUserId(shopCartDto.getUserId());
        boolean deleteFlag = shopCartService.deleteShopCartGoodsById(shopCartId,shopCartDto.getGoodsId());
        if (deleteFlag){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
    }




}
