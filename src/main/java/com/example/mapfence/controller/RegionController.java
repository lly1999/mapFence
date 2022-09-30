package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.IRegionService;
import com.example.mapfence.entity.Region;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-09-26
 */
@RestController
@RequestMapping("//region")
public class RegionController {

    @Resource
    private IRegionService regionService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody Region region) {
        return regionService.saveOrUpdate(region);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return regionService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return regionService.removeByIds(ids);
    }

    @GetMapping
    public List<Region> findAll() {
        return regionService.list();
    }

    @GetMapping("/{id}")
    public Region findOne(@PathVariable Integer id) {
        return regionService.getById(id);
    }

    @GetMapping("/page")
    public Page<Region> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return regionService.page(new Page<>(pageNum, pageSize));
    }
}

