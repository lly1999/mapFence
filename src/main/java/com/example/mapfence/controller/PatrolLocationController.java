package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.example.mapfence.service.IPatrolLocationService;
import com.example.mapfence.entity.PatrolLocation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-10-13
 */
@Api(tags = "巡查员坐标的增删改查与分页查询")
@RestController
@RequestMapping("//patrol-location")
public class PatrolLocationController {

    @Resource
    private IPatrolLocationService patrolLocationService;

    @ApiOperation(value = "指定巡查员id，查询其最新的坐标")
    @GetMapping("/location/{patrol_id}")
    public String selectLatestLocationById(@PathVariable Integer patrol_id) {
        return patrolLocationService.selectLatestLocationById(patrol_id);
    }

    @ApiOperation(value = "指定巡查员id，查询其今日所有记录坐标")
    @GetMapping("/locations/{patrol_id}")
    public List<String> selectTodayLocations(@PathVariable Integer patrol_id) {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = today_start.format(df);

        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        String end = today_end.format(df);
        return patrolLocationService.selectTodayLocations(patrol_id, start, end);
    }

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    public Boolean save(@RequestBody PatrolLocation patrolLocation) {
        return patrolLocationService.saveOrUpdate(patrolLocation);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolLocationService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return patrolLocationService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<PatrolLocation> findAll() {
        return patrolLocationService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public PatrolLocation findOne(@PathVariable Integer id) {
        return patrolLocationService.getById(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<PatrolLocation> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<PatrolLocation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolLocationService.page(new Page<>(pageNum, pageSize));
    }
}

