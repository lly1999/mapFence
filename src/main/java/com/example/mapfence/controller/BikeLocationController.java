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

import com.example.mapfence.service.IBikeLocationService;
import com.example.mapfence.entity.BikeLocation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-11-08
 */
@Api(tags = "单车管理员坐标表增删改查与分页查询")
@RestController
@RequestMapping("//bike-location")
public class BikeLocationController {

    @Resource
    private IBikeLocationService bikeLocationService;

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    public Boolean save(@RequestBody BikeLocation bikeLocation) {
        return bikeLocationService.saveOrUpdate(bikeLocation);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return bikeLocationService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return bikeLocationService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<BikeLocation> findAll() {
        return bikeLocationService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public BikeLocation findOne(@PathVariable Integer id) {
        return bikeLocationService.getById(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<BikeLocation> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<BikeLocation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return bikeLocationService.page(new Page<>(pageNum, pageSize));
    }
}

