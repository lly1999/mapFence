package com.example.mapfence.service;

import com.example.mapfence.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xavi
 * @since 2022-10-24
 */
public interface IRegionService extends IService<Region> {
    public List<Region> selectByAgency(String agency);
}
