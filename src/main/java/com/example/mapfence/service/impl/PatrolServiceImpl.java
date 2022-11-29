package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.entity.Patrol;
import com.example.mapfence.mapper.PatrolMapper;
import com.example.mapfence.service.IPatrolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.schema.Example;

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

    @Override
    public List<Patrol> selectTelephoneByRegionAndIdentity(String identity, Integer region) {
        QueryWrapper<Patrol> wrapper = new QueryWrapper<>();
        wrapper.select("telephone");
        if(region != null)
            wrapper.eq("current_region", region);
        if(!identity.isEmpty())
            wrapper.eq("identity", identity);
        return patrolMapper.selectList(wrapper);
    }

    public List<Patrol> selectByName(String name) {
        return patrolMapper.selectByName(name);
    }

    @Override
    public List<Patrol> selectByTelephone(String telephone) {
        return patrolMapper.selectByTelephone(telephone);
    }

    @Override
    public List<Patrol> selectByConditions(Integer regionId, String department, String identity) {
        QueryWrapper<Patrol> wrapper = new QueryWrapper<>();
        if(regionId != null)
            wrapper.eq("related_region", regionId);
        if(StringUtils.isNotEmpty(department))
            wrapper.eq("department", department);
        if(StringUtils.isNotEmpty(identity))
            wrapper.eq("identity", identity);
        return patrolMapper.selectList(wrapper);
    }

}
