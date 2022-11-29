package com.example.mapfence.service.dtoService;


import com.example.mapfence.entity.Patrol;
import com.example.mapfence.entity.PatrolLocation;
import com.example.mapfence.entity.PatrolStatus;
import com.example.mapfence.entity.Region;
import com.example.mapfence.entity.dto.PatrolWholeInfo;
import com.example.mapfence.service.impl.PatrolLocationServiceImpl;
import com.example.mapfence.service.impl.PatrolServiceImpl;
import com.example.mapfence.service.impl.PatrolStatusServiceImpl;
import com.example.mapfence.service.impl.RegionServiceImpl;
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
    @Resource
    RegionServiceImpl regionService;

    /**
     * 根据以下线索为条件查询人员完整信息，其中三种状态不能同时满足：atWork,vacation,vacationDefer
     * @param date
     * @param atWork
     * @param vacation
     * @param vacationDefer
     * @param agency
     * @param department
     * @param identity
     * @return
     */
    public List<PatrolWholeInfo> selectByConditions(String date, Boolean atWork,
                                                    Boolean vacation, Boolean vacationDefer, String agency,
                                                    String department, String identity) {
        // 将街道转换为regionId
        List<Region> regions = regionService.selectByAgency(agency);
        // 将regionId,department,identity转换为patrolId
        Map<Integer, Patrol> patrolMap = new HashMap<>();
        for(Region region : regions) {
            for(Patrol patrol : patrolService.selectByConditions(region.getId(), department, identity)) {
                patrolMap.put(patrol.getId(), patrol);
            }
        }
        // 从这些人中找出满足状态条件的
        List<PatrolStatus> patrolStatuses = new ArrayList<>();
        for(Integer patrolId : patrolMap.keySet()) {
            patrolStatuses.addAll(patrolStatusService.selectByConditions(patrolId, date, atWork, vacation, vacationDefer));
        }
        // 构造巡查员完整信息
        List<PatrolWholeInfo> patrolWholeInfos = new ArrayList<>();
        for(PatrolStatus patrolStatus : patrolStatuses) {
            PatrolWholeInfo patrolWholeInfo = new PatrolWholeInfo();
            fillPatrolWholeInfoWithPatrolStatus(patrolWholeInfo, patrolStatus);
            fillPatrolWholeInfoWithPatrol(patrolWholeInfo, patrolMap.get(patrolStatus.getPatrolId()));
            patrolWholeInfos.add(patrolWholeInfo);
        }
        return patrolWholeInfos;
    }


    // 获取所有巡查员今日的状态
    public List<PatrolWholeInfo> findAll() {
        // patrol_id - PatrolWholeInfo
        Map<Integer, PatrolWholeInfo> map = new HashMap<>();

        PatrolWholeInfo patrolWholeInfo;

        // 获取所有patrol，存入map
        List<Patrol> patrols = patrolService.list();
        for(Patrol patrol : patrols) {
            patrolWholeInfo = new PatrolWholeInfo();
            fillPatrolWholeInfoWithPatrol(patrolWholeInfo, patrol);
            map.put(patrol.getId(), patrolWholeInfo);
        }

        // 获取今日日期并转换为String
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(fmt);
        // 获取所有patrol今日的状态，存入map
        List<PatrolStatus> patrolStatuses = patrolStatusService.selectAllByDate(date);
        for(PatrolStatus patrolStatus : patrolStatuses) {
            Integer patrol_id = patrolStatus.getPatrolId();
            if(map.containsKey(patrol_id)){
                patrolWholeInfo = map.get(patrol_id);
                fillPatrolWholeInfoWithPatrolStatus(patrolWholeInfo, patrolStatus);
                map.put(patrol_id, patrolWholeInfo);
            }

        }

        // 获取所有patrol今日的坐标，存入map
        List<PatrolLocation> patrolLocations = patrolLocationService.selectLatestLocations();
        for(PatrolLocation patrolLocation : patrolLocations) {
            Integer patrol_id = patrolLocation.getPatrolId();
            if(map.containsKey(patrol_id)) {
                patrolWholeInfo = map.get(patrol_id);
                fillPatrolWholeInfoWithPatrolLocation(patrolWholeInfo, patrolLocation);
                map.put(patrol_id, patrolWholeInfo);
            }
        }

        return new ArrayList<>(map.values());
    }

    private void fillPatrolWholeInfoWithPatrol(PatrolWholeInfo patrolWholeInfo, Patrol patrol) {
//        PatrolWholeInfo patrolWholeInfo = new PatrolWholeInfo();
        patrolWholeInfo.setPatrol_id(patrol.getId());
        patrolWholeInfo.setName(patrol.getName());
        patrolWholeInfo.setDepartment(patrol.getDepartment());
        patrolWholeInfo.setIdentity(patrol.getIdentity());
        patrolWholeInfo.setTelephone(patrol.getTelephone());
        patrolWholeInfo.setWechat(patrol.getWechat());
        patrolWholeInfo.setRelatedRegion(patrol.getRelatedRegion());
        patrolWholeInfo.setTask(patrolWholeInfo.getTask());
    }

    private void fillPatrolWholeInfoWithPatrolStatus(PatrolWholeInfo patrolWholeInfo, PatrolStatus patrolStatus) {
        patrolWholeInfo.setAtWork(patrolStatus.getAtWork());
        patrolWholeInfo.setVacation(patrolStatus.getVacation());
        patrolWholeInfo.setVacationDefer(patrolStatus.getVacationDefer());
        patrolWholeInfo.setOnWork(patrolStatus.getOnWork());
        patrolWholeInfo.setOffWork(patrolStatus.getOffWork());
    }

    private void fillPatrolWholeInfoWithPatrolLocation(PatrolWholeInfo patrolWholeInfo, PatrolLocation patrolLocation) {
        if(patrolLocation.getLocation() != null)
            patrolWholeInfo.setLocation(patrolLocation.getLocation());
    }
}
