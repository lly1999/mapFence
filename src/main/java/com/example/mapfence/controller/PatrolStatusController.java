package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.IPatrolStatusService;
import com.example.mapfence.entity.PatrolStatus;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-09-26
 */
@Api(tags = "巡查员状态表的增删改查与分页查询")
@RestController
@RequestMapping("//patrol-status")
public class PatrolStatusController {

    @Resource
    private IPatrolStatusService patrolStatusService;

    // 新增或者更新
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    @PostMapping
    public Boolean save(@RequestBody PatrolStatus patrolStatus) {
        return patrolStatusService.saveOrUpdate(patrolStatus);
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
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "Integer", required = true),
    })
    @GetMapping("/page")
    public Page<PatrolStatus> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<PatrolStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolStatusService.page(new Page<>(pageNum, pageSize));
    }
}

