package com.zzkj.dcsystem.controller;

import com.zzkj.dcsystem.controller.utils.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员登陆
 * @author JGZ
 * @Classname AdminController
 * @Date 2019/7/29 16:05
 * @Email 1945282561@qq.com
 */
@Controller
public class AdminController {

    @Autowired
    private Admin admin;
    /**
     * 跳转到操作页面
     * @param adminName
     * @param password
     * @param func
     * @return
     */
    @RequestMapping("/toMain")
    public String toOrdersPage(String adminName, String password, String func, HttpServletRequest request){

        if(admin.getName().equals(adminName) && admin.getPassword().equals(password)){
            if("订单管理".equals(func)){
                Admin admin = new Admin();
                admin.setName(adminName);
                admin.setPassword(password);
                request.getSession().setAttribute("admin",admin);
                return "redirect:/toOrders";
            }
            else if ("菜品管理".equals(func)){
                Admin admin = new Admin();
                admin.setName(adminName);
                admin.setPassword(password);
                request.getSession().setAttribute("admin",admin);
                return "redirect:/toDishes";
            }
            else {
                return "error";
            }
        }
        else {
            return "error";
        }
    }

    @RequestMapping("/toOrders")
    public String toOrders(){
        return "orders";
    }

    @RequestMapping("/toDishes")
    public String toDishes(){
        return "dishes";
    }

}
