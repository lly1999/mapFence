package com.example.mapfence.service;

import com.example.mapfence.entity.StrandedMsg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xavi
 * @since 2022-11-02
 */
public interface IStrandedMsgService extends IService<StrandedMsg> {
    List<StrandedMsg> getStrandedMsgs(String adminTelephone);

    Integer deleteStrandedMsgs(String adminTelephone);
}
