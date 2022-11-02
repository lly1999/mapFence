package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.entity.StrandedMsg;
import com.example.mapfence.mapper.StrandedMsgMapper;
import com.example.mapfence.service.IStrandedMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-11-02
 */
@Service
public class StrandedMsgServiceImpl extends ServiceImpl<StrandedMsgMapper, StrandedMsg> implements IStrandedMsgService {

    @Resource
    StrandedMsgMapper strandedMsgMapper;

    /**
     *
     * @param adminTelephone
     * 查询所有滞留给该admin的消息并将这些信息从数据库删除
     * @return 所有滞留给该admin的消息
     */
    @Override
    public List<StrandedMsg> getStrandedMsgs(String adminTelephone) {
        QueryWrapper<StrandedMsg> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_telephone", adminTelephone);
        return strandedMsgMapper.selectList(wrapper);
    }

    @Override
    public Integer deleteStrandedMsgs(String adminTelephone) {
        QueryWrapper<StrandedMsg> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_telephone", adminTelephone);
        return strandedMsgMapper.delete(wrapper);
    }
}
