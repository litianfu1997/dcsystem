package com.zzkj.dcsystem.utils;

import com.google.gson.Gson;
import com.zzkj.dcsystem.controller.utils.OpenIdAndSessionKey;
import org.springframework.context.annotation.Bean;
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
    /**
     *  public static final String APPID = "wx29c36a9a81bf9220";
     *  public static final String APPSECRET = "349b1d2c13f1bd81c786d1ca40fdeb56";
     */
    public static final String JS_CODE_2_SESSION  = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String APPID = "wx29c36a9a81bf9220";
    public static final String APPSECRET = "349b1d2c13f1bd81c786d1ca40fdeb56";

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
