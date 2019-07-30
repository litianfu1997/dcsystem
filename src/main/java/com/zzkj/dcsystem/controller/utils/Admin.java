package com.zzkj.dcsystem.controller.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 管理员用户名和密码
 * @author JGZ
 * @Classname Admin
 * @Date 2019/7/30 10:20
 * @Email 1945282561@qq.com
 */
@PropertySource("classpath:admin.properties")
@ConfigurationProperties(prefix = "admin")
@Configuration
@Data
public class Admin {
    private String name;
    private String password;
}
