package com.example.mapfence.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mapfence.entity.StrandedMsg;
import com.example.mapfence.websocket.dto.appWebSocketMsg;
import com.example.mapfence.websocket.dto.webWebSocketMsg;
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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket服务端，用于和web、app通信
 * web端的socket key为”admin电话号“
 * web端的message格式   {patrolTelephone : xxx, message : xxx}
 * app端的message格式   {adminTelephone : xxx, message : xxx}
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
    public void onOpen(Session session, @PathParam("telephone") String telephone) throws IOException {
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

                // 管理员上线后检查是否有滞留消息
                Integer cnt = StrandedFuncs.haveStrandedMsgs(telephone);
                if(cnt > 0) {
                    // 获取并发送滞留信息
                    List<StrandedMsg> msgs = StrandedFuncs.getStrandedMsgs(telephone);
                    sendMessage(msgs);
                    // 将滞留信息置为已读状态
                    StrandedFuncs.setMsgsRead(msgs);
                    log.info("管理员重新上线:" + telephone + ",当前在线管理员人数为:" + getWebOnlineCnt());
                    log.info("发送给管理员:" + telephone + "的滞留消息数为：" + cnt);
                }
            }
            log.info("管理员上线:" + telephone + ",当前在线管理员人数为:" + getWebOnlineCnt());
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
            log.info("巡查员上线:" + telephone + ",当前在线巡查员人数为:" + getAppOnlineCnt());
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
                // if收到web信息
                if(telephone.contains("admin")){
                    // 解析message查询目标客户端
                    String patrolTelephone = jsonObject.getString("patrolTelephone");
                    // 向目标客户端发送信息
                    if(!StringUtils.isEmpty(patrolTelephone) && appWebSocketMap.containsKey(patrolTelephone)) {
                        String real_msg = jsonObject.getString("message");
                        // 保存消息到数据库并发送给目标
                        appWebSocketMap.get(patrolTelephone).saveAndSendMessage(patrolTelephone, telephone, real_msg, telephone);
                    }else {
                        log.error("请求的客户端为空或该客户端不在线");
                    }
                    // if收到app信息
                }else {
                    String adminTelephone = jsonObject.getString("adminTelephone");
                    if(!StringUtils.isEmpty(adminTelephone) && webWebSocketMap.containsKey(adminTelephone)) {
                        String real_msg = jsonObject.getString("message");
                        // 保存消息到数据库并发送给目标
                        webWebSocketMap.get(adminTelephone).saveAndSendMessage(telephone, adminTelephone, real_msg, telephone);
                    }else {
                        // web端离线的情况，将滞留的消息保存到数据库
                        String msg = jsonObject.getString("message");
                        StrandedFuncs.saveStrandedMsg(telephone, adminTelephone, msg, telephone);
                        log.error("Web端不在线，将滞留消息保存到数据库");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(List<StrandedMsg> msgs) throws IOException {
        String jsonMsg = JSONObject.toJSONString(msgs);
        this.session.getBasicRemote().sendText(jsonMsg);
    }

    public void saveAndSendMessage(String patrolTelephone, String adminTelephone, String real_msg, String sender) throws IOException {
        // 将消息存进数据库
        StrandedFuncs.saveStrandedMsg(patrolTelephone, adminTelephone, real_msg, sender);
        StrandedMsg msg = StrandedFuncs.getOneMsg(adminTelephone, patrolTelephone, sender).get(0);
//        // 将消息置为已读，决定权通过http接口留给前端管理员
//        StrandedFuncs.setMsgRead(msg);
        String jsonMsg = JSONObject.toJSONString(msg);
        this.session.getBasicRemote().sendText(jsonMsg);
    }

//    public void sendMessageToWeb(String patrolTelephone, String message) throws IOException {
//        webWebSocketMsg webSocketMsg = new webWebSocketMsg();
//        webSocketMsg.setPatrolTelephone(patrolTelephone);
//        webSocketMsg.setMessage(message);
//        String jsonMsg = JSONObject.toJSONString(webSocketMsg);
//        this.session.getBasicRemote().sendText(jsonMsg);
//    }
//
//    public void sendStrandedMessagesToWeb(List<StrandedMsg> msgs) throws IOException {
//        String jsonMsg = JSONObject.toJSONString(msgs);
//        this.session.getBasicRemote().sendText(jsonMsg);
//    }
//
//    public void sendMessageToApp(String adminTelephone, String message) throws IOException {
//        appWebSocketMsg webSocketMsg = new appWebSocketMsg();
//        webSocketMsg.setAdminTelephone(adminTelephone);
//        webSocketMsg.setMessage(message);
//        String jsonMsg = JSONObject.toJSONString(webSocketMsg);
//        this.session.getBasicRemote().sendText(jsonMsg);
//    }

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