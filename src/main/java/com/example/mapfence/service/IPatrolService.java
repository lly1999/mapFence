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
 * @since 2022-09-26
 */
public interface IPatrolService extends IService<Patrol> {
    public List<Patrol> selectByName(String name);
}
