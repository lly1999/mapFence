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

import com.example.mapfence.service.IBikeService;
import com.example.mapfence.entity.Bike;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-11-08
 */
@Api(tags = "单车管理员表增删改查与分页查询")
@RestController
@RequestMapping("//bike")
public class BikeController {

    @Resource
    private IBikeService bikeService;

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    public Boolean save(@RequestBody Bike bike) {
        return bikeService.saveOrUpdate(bike);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return bikeService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return bikeService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<Bike> findAll() {
        return bikeService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public Bike findOne(@PathVariable Integer id) {
        return bikeService.getById(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<Bike> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Bike> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return bikeService.page(new Page<>(pageNum, pageSize));
    }
}

