package com.zzkj.dcsystem.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket的服务工具
 * @author JGZ
 * @Classname WebSocketServer
 * @Date 2019/7/26 10:01
 * @Email 1945282561@qq.com
 */
@ServerEndpoint(value = "/webSocket")
@Component
public class WebSocketServer {
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private  final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public WebSocketServer(){
        log.info("初始化");
    }

    /**
     * 在打开连接时调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        //绑定对象
        this.session = session;
        //将本次连接的对象加入socket集合中
        webSocketServers.add(this);
        log.info("有新客户端连接");
    }

    /**
     * 在关闭连接时调用
     */
    @OnClose
    public void onClose(){
        webSocketServers.remove(this);
        log.info("有客户端退出");
    }

    /**
     * 客户端发送消息到服务端调用此方法
     * @param string
     * @param session
     */
    @OnMessage
    public void onMessage(String string,Session session){
        log.info("来自客户端的消息:"+ string);
    }

    /**
     * 出错时调用方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error){
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息给客户端
     * @param massage
     */
    public void sendMassage(String massage) throws IOException {
        session.getBasicRemote().sendText(massage);
    }

    /**
     * 批量发送数据
     * @param massage
     */
    public static void sendInfo(String massage){
        for (WebSocketServer webSocketServer:webSocketServers){
            try {
                webSocketServer.sendMassage(massage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
