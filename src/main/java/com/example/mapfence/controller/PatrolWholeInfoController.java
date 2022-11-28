package com.example.mapfence.controller;


import com.example.mapfence.entity.dto.PatrolWholeInfo;
import com.example.mapfence.service.dtoService.PatrolWholeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
