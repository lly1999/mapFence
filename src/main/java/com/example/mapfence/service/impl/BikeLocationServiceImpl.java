package com.example.mapfence.service.impl;

import com.example.mapfence.entity.BikeLocation;
import com.example.mapfence.mapper.BikeLocationMapper;
import com.example.mapfence.service.IBikeLocationService;
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
public class BikeLocationServiceImpl extends ServiceImpl<BikeLocationMapper, BikeLocation> implements IBikeLocationService {

}
