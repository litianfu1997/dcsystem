package com.zzkj.dcsystem.controller;

import com.google.gson.Gson;
import com.zzkj.dcsystem.controller.utils.DcOrdersQueryVo;
import com.zzkj.dcsystem.dto.*;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.service.DcOrdersService;
import com.zzkj.dcsystem.service.impl.DcOrdersServiceServiceImpl;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.rocketmq.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 15:52
**/

@Controller
public class DcOrdersController {

    @Autowired
    private DcOrdersServiceServiceImpl ordersService;

    @Autowired
    private DcOrdersService dcOrdersService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    DefaultMQProducer defaultMQProducer;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *插入一条订单
     * @param ordersGoodsDto
     * @return
     */
    @RequestMapping("/order/insertOrder.action")
    public @ResponseBody
    ResponseMessage insertOrder(@RequestBody OrdersGoodsDto ordersGoodsDto){

        OrdersDto orders =new OrdersDto();
        orders.setOrdersId(UUID.randomUUID().toString());
        orders.setUserId(ordersGoodsDto.getUserId());
        orders.setLinkMan(ordersGoodsDto.getLinkMan());
        orders.setPhone(ordersGoodsDto.getPhone());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(new Date());
        orders.setCreateDate(format);
        orders.setNote(ordersGoodsDto.getNote());
        orders.setStoreAddress(ordersGoodsDto.getStoreAddress());
        orders.setUserAddress(ordersGoodsDto.getUserAddress());
        orders.setCombined(ordersGoodsDto.getCombined());
        orders.setEvaluation(ordersGoodsDto.getEvaluation());
        if (orders!=null){
            boolean b = ordersService.insertOrder(orders);
            List<ShopCartGoodsDto> shopCartGoodsDtos = new ArrayList<>();
            shopCartGoodsDtos = ordersGoodsDto.getGoods();
            for (ShopCartGoodsDto shopCartGoodsDto : shopCartGoodsDtos) {
                String goodsId = shopCartGoodsDto.getGoodsId();
                Integer amount = shopCartGoodsDto.getAmount();
                Float total = shopCartGoodsDto.getTotal();
                MyOrdersDto ordersGoods = new MyOrdersDto();
                ordersGoods.setOrdersId(orders.getOrdersId());
                ordersGoods.setGoodsId(goodsId);
                ordersGoods.setAmount(amount);
                ordersGoods.setTotal(total);
                boolean orderGoodsFlag = ordersService.insertOrderGoods(ordersGoods);
            }

            Boolean delete = stringRedisTemplate.delete("DcOrdersList:" + orders.getUserId());

            if (b){
                //从数据库中获取刚刚插入数据库的记录
                DcOrders mqOrders =  dcOrdersService.selectDcOrderByOrderId(orders.getOrdersId());
                Gson gson = new Gson();
                String json = gson.toJson(mqOrders);
                //将其放入消息队列
                Message message = new Message("user-orders","white",json.getBytes());
                //发送消息
                try {
                    defaultMQProducer.send(message);
                } catch (MQClientException e) {
                    e.printStackTrace();
                } catch (RemotingException e) {
                    e.printStackTrace();
                } catch (MQBrokerException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //放入缓存
                this.redisCacheUnFinishOrders(mqOrders);

                return new ResponseMessage("success","订单已完成");
            }else {
                return new ResponseMessage("error","订单未完成");

            }
        }

        return new ResponseMessage("error","订单信息为空！");

    }

    /**
     * 查询订单
     * @param orders
     * @return
     */
    @RequestMapping("/order/selectOrder.action")
    public @ResponseBody List<OrdersDto> selectOrder(@RequestBody DcOrders orders) {
        List<OrdersDto> ordersList = ordersService.selectOrderByUserId(orders.getUser().getUserId());
        return ordersList;

    }

    @RequestMapping("/selectOrders")
    public @ResponseBody List<DcOrders> selectOrders(DcOrdersQueryVo queryVo){
        List<DcOrders> list = dcOrdersService.selectOrders(queryVo);
        return list;

    }

    @RequestMapping(value = "/getAllOrder")
    public @ResponseBody List<DcOrders> getAllOrder(){
        List<DcOrders> list = dcOrdersService.getAllOrder();
        return list;
    }

    /**
     * 完成订单
     * @param ordersId
     * @return
     */
    @RequestMapping(value = "/finishOrders")
    public String finishOrders(String ordersId){
        //完成订单
        ordersService.finishOrders(ordersId);

        //重定向到订单页面
        return "redirect:/toOrders";
    }

    /**
     * 获取用户的订单信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/OrdersList")
    public ResponseEntity getDcOrdersList(String userId){
        //看缓存中是否有该用户的订单信息
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String json = ops.get("DcOrdersList:"+ userId);
        List<DcOrders> dcOrdersList = null;
        if(json == null || "".equals(json)){
            //如果没有
            //根据用户id获取订单列表
            dcOrdersList = dcOrdersService.getDcOrdersListByUserId(userId);
            //放入缓存
            this.redisCacheUserOrders(userId,dcOrdersList);
        }
        else{
            dcOrdersList = new Gson().fromJson(json,List.class);
        }
        //返回
        return ResponseEntity.status(HttpStatus.OK).body(dcOrdersList);
    }

    private void redisCacheUnFinishOrders(DcOrders orders){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //从缓存中获取未完成订单的集合
        String unFinishOrdersListStr = ops.get("unFinishOrdersList");
        List<DcOrders> unFinishOrdersList = null;
        Gson gson = new Gson();
        if (unFinishOrdersListStr == null || "".equals(unFinishOrdersListStr)){
            //如果为空，新建未完成订单集合
            unFinishOrdersList = new ArrayList<DcOrders>();
            //添加新订单
            unFinishOrdersList.add(orders);
        }
        else {
            //如果不为空,转成对象
            unFinishOrdersList = gson.fromJson(unFinishOrdersListStr, List.class);
            //添加新订单
            unFinishOrdersList.add(orders);
        }
        //将集合json化
        String json = gson.toJson(unFinishOrdersList);
        ops.set("unFinishOrdersList",json);
    }


    /**
     * 缓存用户的userId和订单列表
     * @param userId
     * @param dcOrdersList
     */
    private void redisCacheUserOrders(String userId,List<DcOrders> dcOrdersList){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //将订单列表json化
        Gson gson = new Gson();
        String json = gson.toJson(dcOrdersList);
        //放入缓存中
        ops.set( "DcOrdersList:"+ userId,json);
        //设置7天过期
        stringRedisTemplate.expire("DcOrdersList:"+ userId  ,604800, TimeUnit.SECONDS);
    }

}
