package com.zzkj.dcsystem.utils;

import com.zzkj.dcsystem.config.WxConfig;
import com.zzkj.dcsystem.utils.wxpay.WXPay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 14:46
 */
public class WXPayFun {
    public static void main(String[] args) throws Exception {

        WxConfig config = new WxConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        //商品描述
        data.put("body", "紫竹科技");
        //商户订单号
        data.put("out_trade_no", "2016090910595900000012");
        //标价币种
        data.put("fee_type", "CNY");
        //交易金额
        //交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
        //外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
        data.put("total_fee", "1");
        //调用微信支付API的机器IP
        data.put("spbill_create_ip", "127.0.0.1");
        //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        // 此处指定为小程序支付
        data.put("trade_type", "JSAPI");
        //用户标识id
        data.put("openid","");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
