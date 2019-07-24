package com.zzkj.dcsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzkj.dcsystem.controller.utils.OpenIdAndSessionKey;
import com.zzkj.dcsystem.controller.utils.RawData;
import com.zzkj.dcsystem.service.DcUserService;
import com.zzkj.dcsystem.utils.WxTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户逻辑
 * @author JGZ
 * @Classname DcUserController
 * @Date 2019/7/23 12:00
 * @Email 1945282561@qq.com
 */
@Controller
public class DcUserController {

    @Autowired
    DcUserService dcUserService;

    @RequestMapping(value = "/userLogin")
    public ResponseEntity<Map<String, String>> userLogin(String code, String rawData, String signature, String encrypteData, String iv){

        ObjectMapper mapper = new ObjectMapper();

        RawData data = null;
        OpenIdAndSessionKey openidAndSessionkey = null;
        String openid = null;
        String sessionKey = null;
        String phoneNumber = null;

        try {
            //获取数据
            if (rawData != null && !"".equals(rawData)){
                data = mapper.readValue(rawData,RawData.class);
            }
            //调用工具获取openid和sessionkey
            openidAndSessionkey = WxTools.getOpenidAndSessionkey(code);
            openid = openidAndSessionkey.getOpenid();
            sessionKey = openidAndSessionkey.getSession_key();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("获取用户信息失败！！");
        }
        //插入用户
        String userId = dcUserService.insertUser(data, openid);

        //缓存openid, sessionKey, userId


        //返回数据
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        map.put("userId",userId);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }


    public DcUserService getDcUserService() {
        return dcUserService;
    }

    public void setDcUserService(DcUserService dcUserService) {
        this.dcUserService = dcUserService;
    }
}
