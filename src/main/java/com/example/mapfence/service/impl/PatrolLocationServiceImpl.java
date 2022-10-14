package com.example.mapfence.service.impl;

import com.example.mapfence.entity.PatrolLocation;
import com.example.mapfence.mapper.PatrolLocationMapper;
import com.example.mapfence.service.IPatrolLocationService;
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
 * @since 2022-10-13
 */
@Service
public class PatrolLocationServiceImpl extends ServiceImpl<PatrolLocationMapper, PatrolLocation> implements IPatrolLocationService {

    @Resource
    PatrolLocationMapper patrolLocationMapper;

    public String selectLatestLocationById(Integer patrol_id) {
        return patrolLocationMapper.selectLatestLocationById(patrol_id);
    }

    public List<String> selectTodayLocations(Integer patrol_id, String today_start, String today_end) {
        return patrolLocationMapper.selectTodayLocations(patrol_id, today_start, today_end);
    }
}
