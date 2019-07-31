package com.zzkj.dcsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试socket连接的类，后期会换
 * @author JGZ
 * @Classname SocketController
 * @Date 2019/7/26 10:42
 * @Email 1945282561@qq.com
 */
@Controller
public class SocketController {

    @RequestMapping(value = "/toSocket")
    public String toSocket(){
        return "socket";
    }
}
