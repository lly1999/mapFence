package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.example.mapfence.service.IPatrolService;
import com.example.mapfence.entity.Patrol;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-10-16
 */
@Api(tags = "巡查员表的增删改查与分页查询")
@RestController
@RequestMapping("//patrol")
public class PatrolController {

    @Resource
    private IPatrolService patrolService;

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    public Boolean save(@RequestBody Patrol patrol) {
        return patrolService.saveOrUpdate(patrol);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return patrolService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<Patrol> findAll() {
        return patrolService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public Patrol findOne(@PathVariable Integer id) {
        return patrolService.getById(id);
    }

    @ApiOperation(value = "按姓名字段查询指定记录，若有重名全部返回")
    @GetMapping("/name/{name}")
    public List<Patrol> findByName(@PathVariable String name) {
        return patrolService.selectByName(name);
    }

    @ApiOperation(value = "按电话号码字段查询指定记录，若有重名全部返回")
    @GetMapping("/telephone/{telephone}")
    public List<Patrol> findByTelephone(@PathVariable String telephone) {
        return patrolService.selectByTelephone(telephone);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<Patrol> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Patrol> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolService.page(new Page<>(pageNum, pageSize));
    }
}

