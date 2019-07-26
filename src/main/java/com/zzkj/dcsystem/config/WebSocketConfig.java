package com.zzkj.dcsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket配置类
 * @author JGZ
 * @Classname WebSocketConfig
 * @Date 2019/7/26 9:58
 * @Email 1945282561@qq.com
 */
@Configuration
public class WebSocketConfig {

    /**
     * 实例化服务对象
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
