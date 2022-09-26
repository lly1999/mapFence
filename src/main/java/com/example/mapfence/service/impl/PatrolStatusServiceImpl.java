package com.example.mapfence.service.impl;

import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.mapper.PatrolStatusMapper;
import com.example.mapfence.service.IPatrolStatusService;
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
public class PatrolStatusServiceImpl extends ServiceImpl<PatrolStatusMapper, PatrolStatus> implements IPatrolStatusService {

}
