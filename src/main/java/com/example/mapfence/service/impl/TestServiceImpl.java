package com.example.mapfence.service.impl;

import com.example.mapfence.entity.Test;
import com.example.mapfence.mapper.TestMapper;
import com.example.mapfence.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-09-24
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
