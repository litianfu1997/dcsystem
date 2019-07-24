package com.zzkj.dcsystem.utils;

import com.google.gson.Gson;
import com.zzkj.dcsystem.controller.utils.OpenIdAndSessionKey;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务端微信工具
 * @author JGZ
 * @Classname WxTools
 * @Date 2019/7/23 14:58
 * @Email 1945282561@qq.com
 */
public class WxTools {

    public static final String JS_CODE_2_SESSION  = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String APPID = "wxa3f04bd3ab6a3018";
    public static final String APPSECRET = "de5c0b0ae4bc4faa21890123902a627b";

    /**
     *  获取openid和session_key
     * @param code 用户登陆码
     * @return OpenIdAndSessionKey
     */
    public static OpenIdAndSessionKey getOpenidAndSessionkey(String code){
        //创建请求实体类
        RestTemplate restTemplate = new RestTemplate();
        //封装数据
        Map<String,String> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID +
                "&secret=" + APPSECRET +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String openidAndSessionKey = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();
        OpenIdAndSessionKey json = gson.fromJson(openidAndSessionKey, OpenIdAndSessionKey.class);
        return json;
    }


}
