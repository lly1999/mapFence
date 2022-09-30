package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.IPatrolService;
import com.example.mapfence.entity.Patrol;

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
@RequestMapping("//patrol")
public class PatrolController {

    @Resource
    private IPatrolService patrolService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody Patrol patrol) {
        return patrolService.saveOrUpdate(patrol);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return patrolService.removeByIds(ids);
    }

    @GetMapping
    public List<Patrol> findAll() {
        return patrolService.list();
    }

    @GetMapping("/{id}")
    public Patrol findOne(@PathVariable Integer id) {
        return patrolService.getById(id);
    }

    @GetMapping("/page")
    public Page<Patrol> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Patrol> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolService.page(new Page<>(pageNum, pageSize));
    }
}

