package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.entity.Patrol;
import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.mapper.PatrolMapper;
import com.example.mapfence.mapper.PatrolStatusMapper;
import com.example.mapfence.service.IPatrolStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Service
public class PatrolStatusServiceImpl extends ServiceImpl<PatrolStatusMapper, PatrolStatus> implements IPatrolStatusService {
    @Resource
    PatrolStatusMapper patrolStatusMapper;

    @Resource
    PatrolMapper patrolMapper;

    @Override
    public boolean isRecordExist(Integer patrolId, String date) {
        return patrolStatusMapper.isRecordExist(patrolId, date) != 0;
    }

    @Override
    public Integer selectIdByMultiId(Integer patrolId, String date) {
        return patrolStatusMapper.selectIdByMultiId(patrolId, date);
    }

    @Override
    public List<PatrolStatus> selectAllByDate(String date) {
        return patrolStatusMapper.selectAllByDate(date);
    }

    @Override
    public PatrolStatus selectStatusByMultiId(Integer patrolId, String date) {
        return patrolStatusMapper.selectStatusByMultiId(patrolId, date);
    }

    @Override
    public Integer telephone2PatrolId(String patrolTelephone) {
        List<Patrol> patrols = patrolMapper.selectByTelephone(patrolTelephone);
        if((patrols != null) && !patrols.isEmpty()) {
            return patrols.get(0).getId();
        }
        return null;
    }

    @Override
    public List<PatrolStatus> selectByPatrolIdAndDate(Integer patrolId, String date) {
        QueryWrapper<PatrolStatus> wrapper = new QueryWrapper<>();
        wrapper.eq("patrol_id", patrolId);
        if(StringUtils.isNotEmpty(date))
            wrapper.eq("date", date);
        return patrolStatusMapper.selectList(wrapper);
    }

    @Override
    public void setStatus(String patrolTelephone, Integer status) {
        Integer patrolId = telephone2PatrolId(patrolTelephone);

        // 当前日期和时间
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = today.format(formatter);

        PatrolStatus patrolStatus = new PatrolStatus();
        QueryWrapper<PatrolStatus> wrapper = new QueryWrapper<>();
        // 上班打卡，在岗
        if(status == 0) {
            if(!selectByPatrolIdAndDate(patrolId, dateString).isEmpty()) {
                log.error("该巡查员当天已有记录，无法再打卡");
            }else {
                patrolStatus.setPatrolId(patrolId);
                patrolStatus.setDate(today);
                patrolStatus.setOnWork(now);
                patrolStatus.setAtWork(true);
                patrolStatus.setVacation(false);
                patrolStatus.setVacationDefer(false);
                patrolStatusMapper.insert(patrolStatus);
            }
        }
        // 打卡下班
        else if(status == 1) {
            if(selectByPatrolIdAndDate(patrolId, dateString).isEmpty()) {
                log.error("该巡查员当天还没有上班打卡记录，无法下班");
            }
            else {
                patrolStatus = selectByPatrolIdAndDate(patrolId, dateString).get(0);
                patrolStatus.setOffWork(now);
                wrapper.eq("patrol_id", patrolId);
                wrapper.eq("date", dateString);
                patrolStatusMapper.update(patrolStatus, wrapper);
            }
        }
        // 轮休
        else if(status == 2) {
            if(!selectByPatrolIdAndDate(patrolId, dateString).isEmpty()) {
                log.error("该巡查员当天已有记录，无法设置轮休状态");
            }
            else {
                LocalDate _date = LocalDate.parse(dateString);
                patrolStatus.setPatrolId(patrolId);
                patrolStatus.setDate(_date);
                patrolStatus.setAtWork(false);
                patrolStatus.setVacation(false);
                patrolStatus.setVacationDefer(true);
                patrolStatusMapper.insert(patrolStatus);
            }
        }
        // 请假
        else if (status == 3) {
            if(!selectByPatrolIdAndDate(patrolId, dateString).isEmpty()) {
                log.error("该巡查员当天已有记录，无法设置请假状态");
            }
            else {
                LocalDate _date = LocalDate.parse(dateString);
                patrolStatus.setPatrolId(patrolId);
                patrolStatus.setDate(_date);
                patrolStatus.setAtWork(false);
                patrolStatus.setVacation(true);
                patrolStatus.setVacationDefer(false);
                patrolStatusMapper.insert(patrolStatus);
            }
        }
    }

    @Override
    public List<PatrolStatus> selectByConditions(Integer patrolId, String date, Boolean atWork, Boolean vacation, Boolean vacationDefer) {
        QueryWrapper<PatrolStatus> wrapper = new QueryWrapper<>();
        wrapper.eq("patrol_id", patrolId);
        if(StringUtils.isNotEmpty(date))
            wrapper.eq("date", date);
        if(atWork != null)
            wrapper.eq("at_work", atWork);
        if(vacation != null)
            wrapper.eq("vacation", vacation);
        if(vacationDefer != null)
            wrapper.eq("vacation_defer", vacationDefer);
        return patrolStatusMapper.selectList(wrapper);
    }


}
