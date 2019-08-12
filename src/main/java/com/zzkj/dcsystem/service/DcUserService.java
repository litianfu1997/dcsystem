package com.zzkj.dcsystem.service;

import com.zzkj.dcsystem.controller.utils.RawData;
import com.zzkj.dcsystem.entity.DcUser;

/**
 * 用户业务层接口
 * @author JGZ
 * @Classname DcUserService
 * @Date 2019/7/23 14:39
 * @Email 1945282561@qq.com
 */
public interface DcUserService {

    /**
     * 根据用户的openid查询用户
     * @param openId 微信根据登陆码返回的openid
     * @return 带有userId的对象
     */
    public DcUser selectDcUserByOpenId(String openId);

    /**
     * 将用户在小程序下的openid和用户的开放信息插入数据库
     * @param data 用户的非敏感数据
     * @param openid 用户的开放id
     * @return 如果是新用户则插入数据库并返回userid,如果用户已存在则直接返回userid
     */
    public String insertUser(RawData data, String openid);


}
