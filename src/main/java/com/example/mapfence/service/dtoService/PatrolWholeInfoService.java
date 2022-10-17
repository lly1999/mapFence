package com.example.mapfence.service.dtoService;


import com.example.mapfence.entity.Patrol;
import com.example.mapfence.entity.PatrolLocation;
import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.entity.dto.PatrolWholeInfo;
import com.example.mapfence.service.impl.PatrolLocationServiceImpl;
import com.example.mapfence.service.impl.PatrolServiceImpl;
import com.example.mapfence.service.impl.PatrolStatusServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  PatrolWholeInfo复合业务的服务层实现
 *  用于整合Patrol、PatrolStatus、PatrolLocation三表数据作统一返回
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Service
public class PatrolWholeInfoService {
    @Resource
    PatrolServiceImpl patrolService;
    @Resource
    PatrolStatusServiceImpl patrolStatusService;
    @Resource
    PatrolLocationServiceImpl patrolLocationService;

    public List<PatrolWholeInfo> findAll() {
        // patrol_id - PatrolWholeInfo
        Map<Integer, PatrolWholeInfo> map = new HashMap<>();

        // 获取所有patrol，存入map
        List<Patrol> patrols = patrolService.list();
        for(Patrol patrol : patrols) {
            map.put(patrol.getId(), fillPatrolWholeInfoWithPatrol(patrol));
        }

        // 获取今日日期并转换为String
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(fmt);
        // 获取所有patrol今日的状态，存入map
        List<PatrolStatus> patrolStatuses = patrolStatusService.selectAllByDate(date);
        for(PatrolStatus patrolStatus : patrolStatuses) {
            Integer patrol_id = patrolStatus.getPatrolId();
            if(map.containsKey(patrol_id))
                map.put(patrol_id, fillPatrolWholeInfoWithPatrolStatus(map.get(patrol_id), patrolStatus));
        }

        // 获取所有patrol今日的坐标，存入map
        List<PatrolLocation> patrolLocations = patrolLocationService.selectLatestLocations();
        for(PatrolLocation patrolLocation : patrolLocations) {
            Integer patrol_id = patrolLocation.getPatrolId();
            if(map.containsKey(patrol_id))
                map.put(patrol_id, fillPatrolWholeInfoWithPatrolLocation(map.get(patrol_id), patrolLocation));
        }

        return new ArrayList<>(map.values());
    }

    private PatrolWholeInfo fillPatrolWholeInfoWithPatrol(Patrol patrol) {
        PatrolWholeInfo patrolWholeInfo = new PatrolWholeInfo();
        patrolWholeInfo.setPatrol_id(patrol.getId());
        patrolWholeInfo.setName(patrol.getName());
        patrolWholeInfo.setDepartment(patrol.getDepartment());
        patrolWholeInfo.setIdentity(patrol.getIdentity());
        patrolWholeInfo.setTelephone(patrol.getTelephone());
        patrolWholeInfo.setWechat(patrol.getWechat());
        patrolWholeInfo.setRelatedRegion(patrol.getRelatedRegion());
        patrolWholeInfo.setTask(patrolWholeInfo.getTask());
        return patrolWholeInfo;
    }

    private PatrolWholeInfo fillPatrolWholeInfoWithPatrolStatus(PatrolWholeInfo patrolWholeInfo, PatrolStatus patrolStatus) {
        patrolWholeInfo.setAtWork(patrolStatus.getAtWork());
        patrolWholeInfo.setVacation(patrolStatus.getVacation());
        patrolWholeInfo.setVacationDefer(patrolStatus.getVacationDefer());
        patrolWholeInfo.setOnWork(patrolStatus.getOnWork());
        patrolWholeInfo.setOffWork(patrolStatus.getOffWork());
        return patrolWholeInfo;
    }

    private PatrolWholeInfo fillPatrolWholeInfoWithPatrolLocation(PatrolWholeInfo patrolWholeInfo, PatrolLocation patrolLocation) {
        if(patrolLocation.getLocation() != null)
            patrolWholeInfo.setLocation(patrolLocation.getLocation());
        return patrolWholeInfo;
    }
}
