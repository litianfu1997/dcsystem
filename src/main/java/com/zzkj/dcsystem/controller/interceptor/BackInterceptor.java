package com.zzkj.dcsystem.controller.interceptor;

import com.zzkj.dcsystem.controller.utils.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台操作的拦截器
 * @author JGZ
 * @Classname BackInterceptor
 * @Date 2019/8/6 11:34
 * @Email 1945282561@qq.com
 */
public class BackInterceptor implements HandlerInterceptor {
    /**
     * 请求前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if(admin == null){
            //如果用户未登陆，不放行
            //跳转到登陆页
            response.sendRedirect(request.getContextPath());
            return false;
        }
        else {
            //如果用户登陆了，放行
            return true;
        }
    }

    /**
     * 请求结束后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 视图渲染后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
