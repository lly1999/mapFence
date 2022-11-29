package com.example.mapfence.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mapfence.entity.dto.PatrolWholeInfo;
import com.example.mapfence.service.dtoService.PatrolWholeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  PatrolWholeController前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Api(tags = "巡查员的数据整合接口，包括巡查员基本信息、巡查员最新状态、巡查员最新坐标")
@RestController
@RequestMapping("//patrol-whole-info")
public class PatrolWholeInfoController {

    @Resource
    PatrolWholeInfoService patrolWholeInfoService;

    @ApiOperation(value = "web端接口：获取所有巡查员的全部最新信息，包括巡查员基本信息、巡查员最新状态、巡查员最新坐标")
    @GetMapping
    public List<PatrolWholeInfo> findAll() {
        return patrolWholeInfoService.findAll();
    }

    @ApiOperation(value = "web端接口：按照复杂条件查询巡查员的完整信息")
    @PostMapping("/select/conditions/")
    public List<PatrolWholeInfo> selectByConditions(@RequestBody String params) {
        JSONObject jsonObject = JSON.parseObject(params);
        String date = jsonObject.getString("date");
        Boolean atWork = jsonObject.getBoolean("atWork");
        Boolean vacation = jsonObject.getBoolean("vacation");
        Boolean vacationDefer = jsonObject.getBoolean("vacationDefer");
        String agency = jsonObject.getString("agency");
        String department = jsonObject.getString("department");
        String identity = jsonObject.getString("identity");
        return patrolWholeInfoService.selectByConditions(date, atWork, vacation, vacationDefer, agency, department, identity);
    }
}
