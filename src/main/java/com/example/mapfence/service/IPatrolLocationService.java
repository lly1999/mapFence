package com.example.mapfence.service;

import com.example.mapfence.entity.PatrolLocation;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xavi
 * @since 2022-10-13
 */
public interface IPatrolLocationService extends IService<PatrolLocation> {

    public String selectLatestLocationById(Integer patrol_id);

    public List<String> selectTodayLocations(Integer patrol_id, String today_start, String today_end);
}
