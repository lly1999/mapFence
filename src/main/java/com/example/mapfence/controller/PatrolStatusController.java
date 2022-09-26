package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RestController
@RequestMapping("//patrol-status")
public class PatrolStatusController {

    @Resource
    private IPatrolStatusService patrolStatusService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody PatrolStatus patrolStatus) {
        return patrolStatusService.saveOrUpdate(patrolStatus);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolStatusService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return patrolStatusService.removeByIds(ids);
    }

    @GetMapping
    public List<PatrolStatus> findAll() {
        return patrolStatusService.list();
    }

    @GetMapping("/{id}")
    public PatrolStatus findOne(@PathVariable Integer id) {
        return patrolStatusService.getById(id);
    }

    @GetMapping("/page")
    public Page<PatrolStatus> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<PatrolStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolStatusService.page(new Page<>(pageNum, pageSize));
    }
}

