package com.zzkj.dcsystem.service.serviceimpl;

import com.zzkj.dcsystem.controller.utils.DcOrdersQueryVo;
import com.zzkj.dcsystem.dao.DcOrdersGoodsMapper;
import com.zzkj.dcsystem.dao.DcOrdersMapper;
import com.zzkj.dcsystem.dao.DcUserMapper;
import com.zzkj.dcsystem.entity.DcOrders;
import com.zzkj.dcsystem.entity.DcOrdersGoods;
import com.zzkj.dcsystem.entity.DcUser;
import com.zzkj.dcsystem.service.DcOrdersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单业务层逻辑实现
 * @author JGZ
 * @Classname DcOrdersServiceImpl
 * @Date 2019/7/24 16:27
 * @Email 1945282561@qq.com
 */
@Service
public class DcOrdersServiceImpl implements DcOrdersService {

    @Autowired
    DcOrdersMapper dcOrdersMapper;

    @Autowired
    DcOrdersGoodsMapper dcOrdersGoodsMapper;

    @Autowired
    DcUserMapper dcUserMapper;
    @Override
    public List<DcOrders> getDcOrdersListByUserId(String userId) {
        //根据用户的userid在数据库中查找出用户
        DcUser dcUser = dcUserMapper.selectDcUserByUserId(userId);
        List<DcOrders> dcOrdersList = null;
        if(dcUser != null){
            //如果用户存在
            //查询出用户的所有订单
            dcOrdersList = dcOrdersMapper.selectOrdersByUserId(dcUser);
            if (dcOrdersList != null){
                //如果订单列表中有该用户的订单记录
                //根据订单查询订单中的商品
                for (DcOrders orders:dcOrdersList) {
                    //给每个订单绑定该用户
                    orders.setUser(dcUser);
                    //查询到每一个订单中的商品列表
                    List<DcOrdersGoods> DcOrdersGoodsList = dcOrdersGoodsMapper.selectDcOrdersGoodsByDcOrders(orders);
                    //将其放入对象中
                    orders.setGoodsList(DcOrdersGoodsList);
                }
            }

        }
        return dcOrdersList;
    }

    @Override
    public DcOrders selectDcOrderByOrderId(String ordersId) {
        //获取订单
        DcOrders dcOrders =  dcOrdersMapper.selectUnDcOrderByOrderId(ordersId);
        //通过订单获取订单中的商品
        if(dcOrders != null){
            List<DcOrdersGoods> DcOrdersGoodsList = dcOrdersGoodsMapper.selectDcOrdersGoodsByDcOrders(dcOrders);
            //将其放入对象中
            dcOrders.setGoodsList(DcOrdersGoodsList);
        }

        return dcOrders;
    }

    @Override
    public List<DcOrders> getAllOrder() {

        List<DcOrders> list = dcOrdersMapper.getAllOrder();
        for (int i = 0;i< list.size();i++){
            List<DcOrdersGoods> dcOrdersGoods = dcOrdersGoodsMapper.selectDcOrdersGoodsByDcOrders(list.get(i));
            list.get(i).setGoodsList(dcOrdersGoods);
        }
        return list;
    }

    @Override
    public List<DcOrders> selectOrders(DcOrdersQueryVo queryVo) {
        queryVo.setLinkman("%" + queryVo.getLinkman() + "%");
        queryVo.setPhone("%" + queryVo.getPhone() + "%");
        List<DcOrders> list = dcOrdersMapper.selectOrders(queryVo);
        for (int i = 0;i< list.size();i++){
            List<DcOrdersGoods> dcOrdersGoods = dcOrdersGoodsMapper.selectDcOrdersGoodsByDcOrders(list.get(i));
            list.get(i).setGoodsList(dcOrdersGoods);
        }
        return list;
    }
}
