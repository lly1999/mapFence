package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.entity.Region;
import com.example.mapfence.mapper.RegionMapper;
import com.example.mapfence.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-10-24
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Resource
    RegionMapper regionMapper;

    @Override
    public List<Region> selectByAgency(String agency) {
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(agency))
            wrapper.eq("agency", agency);
        return regionMapper.selectList(wrapper);
    }
}
