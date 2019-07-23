package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.dao.DcUserMapper;
import com.zzkj.dcsystem.entity.DcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

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
    DcUserMapper dcUserMapper;

    @ResponseBody
    @RequestMapping(value = "/userLogin")
    public String userLogin(String code){
        System.out.println(code);
        DcUser user = new DcUser();
        user.setUserId(UUID.randomUUID().toString());
        dcUserMapper.insertUser(user);
        return "hello";
    }

}
