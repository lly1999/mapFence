package com.example.mapfence.service.impl;

import com.example.mapfence.entity.Bike;
import com.example.mapfence.mapper.BikeMapper;
import com.example.mapfence.service.IBikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-11-08
 */
@Service
public class BikeServiceImpl extends ServiceImpl<BikeMapper, Bike> implements IBikeService {

}
