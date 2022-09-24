package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.ITestService;
import com.example.mapfence.entity.Test;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-09-24
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody Test test) {
        return testService.saveOrUpdate(test);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return testService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return testService.removeByIds(ids);
    }

    @GetMapping
    public List<Test> findAll() {
        return testService.list();
    }

    @GetMapping("/{id}")
    public Test findOne(@PathVariable Integer id) {
        return testService.getById(id);
    }

    @GetMapping("/page")
    public Page<Test> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return testService.page(new Page<>(pageNum, pageSize));
    }
}

