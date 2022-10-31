package com.example.mapfence.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket服务端，用于和web、app通信
 * web端的socket key为”admin“
 * web端的message格式   {toPatrolTele : xxx, message : xxx}
 * by 张渝
 * @since 2022-10-31
 */
@ServerEndpoint("/websocket/{telephone}")
@Component
public class WebSocketServer {

    // 记录日志
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 当前已注册的巡查员数量
    private static AtomicInteger onlineCnt = new AtomicInteger(0);

    // 管理socket池
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    // 当前连接的Session
    private Session session;

    // 当前客户端的电话号，作为Session的索引
    private String telephone;

    // web端的message中代表通信目标的字段
    private String toPatrolTele = "toPatrolTele";

    @OnOpen
    public void onOpen(Session session, @PathParam("telephone") String telephone) {
        this.session = session;
        this.telephone = telephone;
        // 若存在则更新
        if(webSocketMap.containsKey(telephone)) {
            webSocketMap.remove(telephone);
            webSocketMap.put(telephone, this);
        }else {
            webSocketMap.put(telephone, this);
            addOnlineCnt();
        }
        log.info("用户连接:" + telephone + ",当前在线人数为:" + getOnlineCnt());
        try{
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + telephone + ",网络异常!!!!!!");
        }
    }

    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(telephone)) {
            webSocketMap.remove(telephone);
            subOnlineCnt();
        }
        log.info("用户退出:" + telephone + ",当前在线人数为:" + getOnlineCnt());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端" + telephone + "的消息：" + message);
        if(!StringUtils.isEmpty(message)) {
            try{
                // TODO: if收到web信息
                if(telephone.equals("admin")){
                    // 解析message查询目标客户端
                    JSONObject jsonObject = JSON.parseObject(message);
                    String toPatrolTele = jsonObject.getString(this.toPatrolTele);
                    // 向目标客户端发送信息
                    if(!StringUtils.isEmpty(toPatrolTele) && webSocketMap.containsKey(toPatrolTele)) {
                        webSocketMap.get(toPatrolTele).sendMessage(jsonObject.getString("message"));
                    }else {
                        log.error("请求的客户端为空或该客户端不在线");
                    }
                    // TODO: if收到app信息
                }else {
                    if(webSocketMap.containsKey("admin")) {
                        webSocketMap.get("admin").sendMessage(message);
                    }else {
                        log.error("Web端不在线，无法通信");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized AtomicInteger getOnlineCnt() {
        return onlineCnt;
    }

    private static synchronized void addOnlineCnt() {
        WebSocketServer.onlineCnt.getAndIncrement();
    }

    private static synchronized void subOnlineCnt() {
        WebSocketServer.onlineCnt.getAndDecrement();
    }
}