package com.zzkj.dcsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzkj.dcsystem.controller.utils.OpenIdAndSessionKey;
import com.zzkj.dcsystem.controller.utils.RawData;
import com.zzkj.dcsystem.service.DcUserService;
import com.zzkj.dcsystem.controller.utils.WxTools;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private DcUserService dcUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WxTools wxTools;
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @RequestMapping(value = "/userLogin")
    public ResponseEntity<Map<String, String>> userLogin(String code, String rawData, String signature, String encrypteData, String iv){
        ObjectMapper mapper = new ObjectMapper();
        RawData data = null;
        OpenIdAndSessionKey openidAndSessionkey = null;
        String openid = null;
        String sessionKey = null;

        try {
            //获取数据
            if (rawData != null && !"".equals(rawData)){
                data = mapper.readValue(rawData,RawData.class);
            }
            //调用工具获取openid和sessionkey
            openidAndSessionkey = wxTools.getOpenidAndSessionkey(code);
            openid = openidAndSessionkey.getOpenid();
            sessionKey = openidAndSessionkey.getSession_key();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取用户信息失败");
        }
        //插入用户
        String userId = dcUserService.insertUser(data, openid);

        //缓存openid, sessionKey, userId
        redisCache(openid,sessionKey,userId);

        Message message = new Message("user-topic","white",("用户:" + userId + "登陆").getBytes());
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

        //返回数据
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        map.put("userId",userId);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * 缓存openid sessionKey userId
     * @param openid
     * @param sessionKey
     * @param userId
     */
    private void redisCache(String openid,String sessionKey,String userId){
        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
        //根据openid查询用户的userId
        String userIdRedis = (String) ops.get("WEXIN_USER_OPENID_USERID", openid);
        if(userIdRedis != null && !"".equals(userIdRedis)){
            //如果存在
            ops.delete("WEIXIN_USER_OPENID_USERID",openid);
            ops.delete("WEIXIN_USER_USERID_OPENID", userIdRedis);
            ops.delete("WEIXIN_USER_USERID_SESSIONKEY",userIdRedis);
        }
        //缓存新的
        ops.put("WEIXIN_USER_OPENID_USERID",openid,userId);
        ops.put("WEIXIN_USER_USERID_OPENID",userId,openid);
        ops.put("WEIXIN_USER_USERID_SESSIONKEY",userId,sessionKey);
        logger.info("用户:"+userId+" openid:"+openid+" sessionKey:"+sessionKey+" 已缓存");
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public DcUserService getDcUserService() {
        return dcUserService;
    }

    public void setDcUserService(DcUserService dcUserService) {
        this.dcUserService = dcUserService;
    }
}
