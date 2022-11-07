package com.example.mapfence.websocket;

import com.example.mapfence.entity.Patrol;
import com.example.mapfence.entity.StrandedMsg;
import com.example.mapfence.service.IPatrolService;
import com.example.mapfence.service.IStrandedMsgService;
import com.example.mapfence.service.impl.PatrolServiceImpl;
import com.example.mapfence.service.impl.StrandedMsgServiceImpl;
import com.example.mapfence.utils.SpringContextUtils;

import java.util.List;

/**
 * <p>
 *  WebSocketServer中使用的函数类
 *  saveStrandedMsg: 向数据库中插入一条滞留消息
 *  getStrandedMsgs: 从数据库中查询所有滞留消息，并删除
 * </p>
 *
 * @author xavi
 * @since 2022-11-02
 */
public class StrandedFuncs {

    private static final IStrandedMsgService strandedMsgService = SpringContextUtils.getBean(StrandedMsgServiceImpl.class);

    // 新增单条记录
    public static void saveStrandedMsg(String patrolTelephone, String adminTelephone, String message, String sender) {
        StrandedMsg msg = new StrandedMsg();
        msg.setPatrolTelephone(patrolTelephone);
        msg.setAdminTelephone(adminTelephone);
        msg.setMessage(message);
        msg.setSender(sender);
        strandedMsgService.saveOrUpdate(msg);
    }

    // 查询所有记录
    public static List<StrandedMsg> getStrandedMsgs(String adminTelephone) {
        return strandedMsgService.getStrandedMsgs(adminTelephone);
    }

    // 查询单条记录
    public static List<StrandedMsg> getOneMsg(String adminTelephone, String patrolTelephone, String sender) {
        return strandedMsgService.getOneMsg(adminTelephone, patrolTelephone, sender);
    }

    // 删除滞留消息（按管理员电话号）
    public static Integer deleteStrandedMsgs(String adminTelephone) {
        return strandedMsgService.deleteStrandedMsgs(adminTelephone);
    }

    // 查询该admin是否有滞留消息
    public static Integer haveStrandedMsgs(String adminTelephone) {
        return strandedMsgService.getStrandedMsgs(adminTelephone).size();
    }

    // 将消息置为已读状态
    public static void setMsgsRead(List<StrandedMsg> msgs) {
        if(msgs.size() > 0) {
            for (StrandedMsg msg : msgs) {
                Integer id = msg.getId();
                strandedMsgService.updateReadStatus(id);
            }
        }
    }

    public static void setMsgRead(StrandedMsg msg) {
        Integer id = msg.getId();
        strandedMsgService.updateReadStatus(id);
    }
}
