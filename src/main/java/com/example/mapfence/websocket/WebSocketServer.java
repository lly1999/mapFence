package com.example.mapfence.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mapfence.websocket.dto.WebSocketMsg;
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
 * web端的socket key为”admin电话号“
 * web端的message格式   {target : xxx, message : xxx}
 * app端的message格式   {target : xxx, message : xxx}
 * by 张渝
 * @since 2022-10-31
 */
@ServerEndpoint("/websocket/{telephone}")
@Component
public class WebSocketServer {

    // 记录日志
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 当前已注册的巡查员数量
    private static AtomicInteger appOnlineCnt = new AtomicInteger(0);

    // 当前已注册的管理员数量
    private static AtomicInteger webOnlineCnt = new AtomicInteger(0);

    // 巡查员socket池
    private static ConcurrentHashMap<String, WebSocketServer> appWebSocketMap = new ConcurrentHashMap<>();

    // 管理员socket池
    private static ConcurrentHashMap<String, WebSocketServer> webWebSocketMap = new ConcurrentHashMap<>();

    // 当前连接的Session
    private Session session;

    // 当前客户端的电话号，作为Session的索引
    private String telephone;

    @OnOpen
    public void onOpen(Session session, @PathParam("telephone") String telephone) {
        this.session = session;
        this.telephone = telephone;
        // 分为管理员和巡查员
        if(telephone.contains("admin")) {
            // 若存在则更新
            if(webWebSocketMap.containsKey(telephone)) {
                webWebSocketMap.remove(telephone);
                webWebSocketMap.put(telephone, this);
            }else {
                webWebSocketMap.put(telephone, this);
                addWebOnlineCnt();
            }
            log.info("用户连接:" + telephone + ",当前在线巡查员人数为:" + getWebOnlineCnt());
        }
        else {
            // 若存在则更新
            if(appWebSocketMap.containsKey(telephone)) {
                appWebSocketMap.remove(telephone);
                appWebSocketMap.put(telephone, this);
            }else {
                appWebSocketMap.put(telephone, this);
                addAppOnlineCnt();
            }
            log.info("用户连接:" + telephone + ",当前在线管理员人数为:" + getAppOnlineCnt());
        }
        try{
            sendMessage("服务器", "连接成功");
        } catch (IOException e) {
            log.error("用户:" + telephone + ",网络异常!!!!!!");
        }
    }

    @OnClose
    public void onClose() {
        if(telephone.contains("admin")) {
            if (webWebSocketMap.containsKey(telephone)) {
                webWebSocketMap.remove(telephone);
                subWebOnlineCnt();
                log.info("管理员退出:" + telephone + ",当前在线管理员人数为:" + getWebOnlineCnt());
            }
        }
        else {
            if (appWebSocketMap.containsKey(telephone)) {
                appWebSocketMap.remove(telephone);
                subAppOnlineCnt();
                log.info("巡查员退出:" + telephone + ",当前在线巡查员人数为:" + getAppOnlineCnt());
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自" + telephone + "的消息：" + message);
        if(!StringUtils.isEmpty(message)) {
            try{

                JSONObject jsonObject = JSON.parseObject(message);
                String target = jsonObject.getString("target");
                // if收到web信息
                if(telephone.contains("admin")){
                    // 解析message查询目标客户端
                    // 向目标客户端发送信息
                    if(!StringUtils.isEmpty(target) && appWebSocketMap.containsKey(target)) {
                        appWebSocketMap.get(target).sendMessage(telephone, jsonObject.getString("message"));
                    }else {
                        log.error("请求的客户端为空或该客户端不在线");
                    }
                    // if收到app信息
                }else {
                    if(!StringUtils.isEmpty(target) && webWebSocketMap.containsKey(target)) {
                        webWebSocketMap.get(target).sendMessage(telephone, jsonObject.getString("message"));
                    }else {
                        log.error("Web端不在线，无法通信");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String target, String message) throws IOException {
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setTarget(target);
        webSocketMsg.setMessage(message);
        String jsonMsg = JSONObject.toJSONString(webSocketMsg);
        this.session.getBasicRemote().sendText(jsonMsg);
    }

    public static synchronized AtomicInteger getAppOnlineCnt() {
        return appOnlineCnt;
    }

    public static synchronized AtomicInteger getWebOnlineCnt() {
        return webOnlineCnt;
    }

    private static synchronized void addAppOnlineCnt() {
        WebSocketServer.appOnlineCnt.getAndIncrement();
    }

    private static synchronized void addWebOnlineCnt() {
        WebSocketServer.webOnlineCnt.getAndIncrement();
    }

    private static synchronized void subAppOnlineCnt() {
        WebSocketServer.appOnlineCnt.getAndDecrement();
    }

    private static synchronized void subWebOnlineCnt() {
        WebSocketServer.webOnlineCnt.getAndDecrement();
    }
}