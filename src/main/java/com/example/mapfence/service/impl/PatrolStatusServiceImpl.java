package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.entity.Patrol;
import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.mapper.PatrolMapper;
import com.example.mapfence.mapper.PatrolStatusMapper;
import com.example.mapfence.service.IPatrolStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Resource
    PatrolMapper patrolMapper;

    @Override
    public boolean isRecordExist(Integer patrolId, String date) {
        return patrolStatusMapper.isRecordExist(patrolId, date) != 0;
    }

    @Override
    public Integer selectIdByMultiId(Integer patrolId, String date) {
        return patrolStatusMapper.selectIdByMultiId(patrolId, date);
    }

    @Override
    public List<PatrolStatus> selectAllByDate(String date) {
        return patrolStatusMapper.selectAllByDate(date);
    }

    @Override
    public PatrolStatus selectStatusByMultiId(Integer patrolId, String date) {
        return patrolStatusMapper.selectStatusByMultiId(patrolId, date);
    }

    @Override
    public Integer telephone2PatrolId(String patrolTelephone) {
        List<Patrol> patrols = patrolMapper.selectByTelephone(patrolTelephone);
        if((patrols != null) && !patrols.isEmpty()) {
            return patrols.get(0).getId();
        }
        return null;
    }

    @Override
    public List<PatrolStatus> selectByPatrolIdAndDate(Integer patrolId, String date) {
        QueryWrapper<PatrolStatus> wrapper = new QueryWrapper<>();
        wrapper.eq("patrol_id", patrolId);
        wrapper.eq("date", date);
        return patrolStatusMapper.selectList(wrapper);
    }


}
