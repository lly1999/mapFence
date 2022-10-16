package com.example.mapfence.service.impl;

import com.example.mapfence.entity.Patrol;
import com.example.mapfence.mapper.PatrolMapper;
import com.example.mapfence.service.IPatrolService;
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
 * @since 2022-10-16
 */
@Service
public class PatrolServiceImpl extends ServiceImpl<PatrolMapper, Patrol> implements IPatrolService {
    @Resource
    PatrolMapper patrolMapper;

    public List<Patrol> selectByName(String name) {
        return patrolMapper.selectByName(name);
    }

    @Override
    public List<Patrol> selectByTelephone(String telephone) {
        return patrolMapper.selectByTelephone(telephone);
    }
}
