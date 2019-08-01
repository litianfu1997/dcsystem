package com.zzkj.dcsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类
 * @author JGZ
 * @Classname WebConfig
 * @Date 2019/7/31 14:18
 * @Email 1945282561@qq.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置静态资源路径映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/file/");
    }
}
