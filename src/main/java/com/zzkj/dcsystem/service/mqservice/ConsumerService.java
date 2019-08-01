package com.zzkj.dcsystem.service.mqservice;

import com.zzkj.dcsystem.config.MessageEvent;
import com.zzkj.dcsystem.controller.utils.WebSocketServer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息消费者的服务对象
 * @author JGZ
 * @Classname ConsumerService
 * @Date 2019/7/25 16:59
 * @Email 1945282561@qq.com
 */
@Component
public class ConsumerService {

    /**
     * 监听user-topic主题，white标签的消息
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='user-topic' && #event.msgs[0].tags=='white'")
    public void rocketmqMsgListener(MessageEvent event) {
        try {
            List<MessageExt> msgs = event.getMsgs();
            for (MessageExt msg : msgs) {
                //通过webSocket发送给前端
                WebSocketServer.sendInfo( new String(msg.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听user-orders主题，white标签的消息
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='user-orders'&&#event.msgs[0].tags=='white'")
    public void ordersMsgListener(MessageEvent event){
        try {
            List<MessageExt> msgs = event.getMsgs();
            for (MessageExt msg : msgs) {
                //通过webSocket发送给前端
//                System.out.println(new String(msg.getBody()));
                WebSocketServer.sendInfo( new String(msg.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
