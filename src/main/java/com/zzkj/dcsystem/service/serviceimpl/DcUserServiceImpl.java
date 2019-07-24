package com.zzkj.dcsystem.service.serviceimpl;

import com.zzkj.dcsystem.controller.utils.RawData;
import com.zzkj.dcsystem.dao.DcUserMapper;
import com.zzkj.dcsystem.entity.DcUser;
import com.zzkj.dcsystem.service.DcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户的业务层实现
 * @author JGZ
 * @Classname DcUserServiceImpl
 * @Date 2019/7/23 14:38
 * @Email 1945282561@qq.com
 */
@Service
public class DcUserServiceImpl implements DcUserService {

    @Autowired
    DcUserMapper dcUserMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public DcUser selectDcUserByOpenId(String openId) {
        DcUser user = dcUserMapper.selectDcUserByOpenId(openId);
        return user;
    }

    @Override
    public String insertUser(RawData data, String openid) {
        //查询用户是否存在
        DcUser user = this.selectDcUserByOpenId(openid);
        //生成唯一标识
        String uuid = UUID.randomUUID().toString();
        //如果查询结果为空
        if (user == null){
            //插入用户
            DcUser dcUser = new DcUser();
            dcUser.setUserId(uuid);
            dcUser.setOpenId(openid);
            dcUser.setNickName(data.getNickName());
            dcUser.setGender(data.getGender());
            dcUser.setLanguage(data.getLanguage());
            dcUser.setCity(data.getCity());
            dcUser.setProvince(data.getProvince());
            dcUser.setCountry(data.getCountry());
            dcUser.setAvatarUrl(data.getAvatarUrl());
            dcUserMapper.insertUser(dcUser);
            logger.info("用户插入成功");
        }
        else{
            logger.info("用户已存在");
            //直接返回查询到的userid
            return user.getUserId();
        }
        //返回生成的id
        return uuid;
    }



    public DcUserMapper getDcUserMapper() {
        return dcUserMapper;
    }

    public void setDcUserMapper(DcUserMapper dcUserMapper) {
        this.dcUserMapper = dcUserMapper;
    }
}
