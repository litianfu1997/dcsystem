package com.zzkj.dcsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * web配置类
 * @author JGZ
 * @Classname WebConfig
 * @Date 2019/7/31 14:18
 * @Email 1945282561@qq.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String path = "file/";

    /**
     * 配置静态资源路径映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String p = new File(path).getAbsolutePath() + File.separator;
        System.out.println(p);
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + p);
    }
}
