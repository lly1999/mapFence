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

    public boolean isRecordExist(Integer patrolId, String date);

    public Integer selectIdByMultiId(Integer patrolId, String date);

    public List<PatrolStatus> selectAllByDate(String date);

    public PatrolStatus selectStatusByMultiId(Integer patrolId, String date);

    Integer telephone2PatrolId(String patrolTelephone);

    List<PatrolStatus> selectByPatrolIdAndDate(Integer patrolId, String date);

    void setStatus(String patrolTelephone, Integer status, String date);

    List<PatrolStatus> selectByConditions(Integer patrolId, String date, Boolean atWork,
                                          Boolean vacation, Boolean vacationDefer);
}
