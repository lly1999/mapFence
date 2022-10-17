package com.example.mapfence.service.impl;

import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.mapper.PatrolStatusMapper;
import com.example.mapfence.service.IPatrolStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Service
public class PatrolStatusServiceImpl extends ServiceImpl<PatrolStatusMapper, PatrolStatus> implements IPatrolStatusService {
    @Resource
    PatrolStatusMapper patrolStatusMapper;

    @Override
    public boolean isRecordExist(Integer patrol_id, String date) {
        return patrolStatusMapper.isRecordExist(patrol_id, date) != 0;
    }

    @Override
    public Integer selectIdByMultiId(Integer patrol_id, String date) {
        return patrolStatusMapper.selectIdByMultiId(patrol_id, date);
    }

    @Override
    public List<PatrolStatus> selectAllByDate(String date) {
        return patrolStatusMapper.selectAllByDate(date);
    }

    @Override
    public PatrolStatus selectStatusByMultiId(Integer patrol_id, String date) {
        return patrolStatusMapper.selectStatusByMultiId(patrol_id, date);
    }
}
