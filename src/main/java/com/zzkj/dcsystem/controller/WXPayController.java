package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.config.WxConfig;
import com.zzkj.dcsystem.dao.DcUserMapper;
import com.zzkj.dcsystem.dto.WXEntity;
import com.zzkj.dcsystem.dto.WXPayDto;
import com.zzkj.dcsystem.entity.DcUser;
import com.zzkj.dcsystem.utils.wxpay.WXPay;
import com.zzkj.dcsystem.utils.wxpay.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-08 16:22
 */
@Controller
public class WXPayController {
    @Autowired
    private DcUserMapper userMapper;

    @Autowired
    private WxConfig config;

    private  String spbillCreateIp;
    private  String notifyUrl;

    @RequestMapping("/WXPay")
    public @ResponseBody WXPayDto pay(@RequestBody WXEntity entity) throws Exception {

        //通过userId查询openid
        DcUser User = userMapper.selectDcUserByUserId(entity.getUserId());
        WXPay wxpay = new WXPay(config,true,true);
        Map<String, String> data = new HashMap<String, String>();
        //商品描述
        data.put("body", "紫竹科技");
        //商户订单号（自定义生成最好是时间戳）
        data.put("out_trade_no", String.valueOf(WXPayUtil.getCurrentTimestampMs()));
        //标价币种
        data.put("fee_type", "CNY");
        //交易金额
        //交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
        //外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
        data.put("total_fee", String.valueOf(entity.getMoney()));
        //调用微信支付API的机器IP（服务器ip）
        data.put("spbill_create_ip", spbillCreateIp);
        //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        data.put("notify_url", notifyUrl);
        // 此处指定为小程序支付
        data.put("trade_type", "JSAPI");
        //用户标识id
        data.put("openid", User.getOpenId());
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
