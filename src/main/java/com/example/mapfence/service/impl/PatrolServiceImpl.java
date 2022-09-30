package com.example.mapfence.service.impl;

import com.example.mapfence.entity.Patrol;
import com.example.mapfence.mapper.PatrolMapper;
import com.example.mapfence.service.IPatrolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-09-26
 */
@Service
public class PatrolServiceImpl extends ServiceImpl<PatrolMapper, Patrol> implements IPatrolService {

}
