package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.example.mapfence.service.IPatrolStatusService;
import com.example.mapfence.entity.PatrolStatus;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Api(tags = "巡查员状态表的增删改查与分页查询")
@RestController
@RequestMapping("//patrol-status")
public class PatrolStatusController {

    @Resource
    private IPatrolStatusService patrolStatusService;

    @ApiOperation(value = "给定patrol_id和date，返回指定巡查员某天的状态")
    @GetMapping("/status/{patrol_id}/{date}")
    public PatrolStatus selectStatusByMultiId(@PathVariable Integer patrol_id, @PathVariable String date) {
        return patrolStatusService.selectStatusByMultiId(patrol_id, date);
    }

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "按照patrol_id与date作为联合主键，新增或更新记录")
    public Boolean save(@RequestBody PatrolStatus patrolStatus) {
        Integer patrol_id = patrolStatus.getPatrolId();
        // 获取date并转换为String
        LocalDate localDate = patrolStatus.getDate();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(fmt);

        if(patrolStatusService.isRecordExist(patrol_id, date)){
            Integer id = patrolStatusService.selectIdByMultiId(patrol_id, date);
            patrolStatus.setId(id);
            return patrolStatusService.updateById(patrolStatus);
        }
        else
            return patrolStatusService.save(patrolStatus);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolStatusService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return patrolStatusService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<PatrolStatus> findAll() {
        return patrolStatusService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public PatrolStatus findOne(@PathVariable Integer id) {
        return patrolStatusService.getById(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<PatrolStatus> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<PatrolStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolStatusService.page(new Page<>(pageNum, pageSize));
    }
}

