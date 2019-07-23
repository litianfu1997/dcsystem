package com.demo.dcsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.element.NestingKind;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-23 10:32
 */
@Controller
public class TestController {
    @RequestMapping("/hello")
    public String test(){
        return "hello";
    }
}
