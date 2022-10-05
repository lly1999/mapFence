package com.example.mapfence.service.impl;

import com.example.mapfence.entity.PatrolLocation;
import com.example.mapfence.mapper.PatrolLocationMapper;
import com.example.mapfence.service.IPatrolLocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-10-05
 */
@Service
public class PatrolLocationServiceImpl extends ServiceImpl<PatrolLocationMapper, PatrolLocation> implements IPatrolLocationService {

}
