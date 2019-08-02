package com.zzkj.dcsystem;

import com.zzkj.dcsystem.controller.utils.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DcsystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DcsystemApplication.class, args);
        WebSocketServer.setApplicationContext(applicationContext);
    }

}
