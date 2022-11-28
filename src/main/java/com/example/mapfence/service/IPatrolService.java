package com.example.mapfence.service;

import com.example.mapfence.entity.Patrol;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xavi
 * @since 2022-10-16
 */
public interface IPatrolService extends IService<Patrol> {
    public List<Patrol> selectTelephoneByRegionAndIdentity(String identity, Integer region);

    public List<Patrol> selectByName(String name);

    public List<Patrol> selectByTelephone(String telephone);

    public List<Patrol> selectByConditions(Integer regionId, String department, String identity);
}
