package com.zzkj.dcsystem.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zzkj.dcsystem.controller.utils.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid连接池的配置类
 * @author JGZ
 * @Classname DruidConfig
 * @Date 2019/7/23 12:58
 * @Email 1945282561@qq.com
 */
@Configuration
@EnableConfigurationProperties(Admin.class)
public class DruidConfig {

    @Autowired
    private Admin admin;
    /**
     * 在容器中注入Druid数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
       return new DruidDataSource();
    }

    /**
     * 配置druid监控
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String,String> map = new HashMap<>();
        map.put("loginUsername",admin.getName());
        map.put("loginPassword",admin.getPassword());
        bean.setInitParameters(map);
        return bean;
    }

    @Bean
    public FilterRegistrationBean wbStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String,String> map = new HashMap<>();
        map.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(map);
        bean.addUrlPatterns("/*");
        return bean;
    }


}
