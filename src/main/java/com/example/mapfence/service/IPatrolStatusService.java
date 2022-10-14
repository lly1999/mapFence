package com.example.mapfence.service;

import com.example.mapfence.entity.PatrolStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
public interface IPatrolStatusService extends IService<PatrolStatus> {

    public boolean isRecordExist(Integer patrol_id, String date);

    public Integer selectIdByMultiId(Integer patrol_id, String date);

    public List<PatrolStatus> selectAllByDate(String date);

    public PatrolStatus selectStatusByMultiId(Integer patrol_id, String date);
}
