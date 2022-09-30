package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.IProblemRecordService;
import com.example.mapfence.entity.ProblemRecord;

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
@RequestMapping("//problem-record")
public class ProblemRecordController {

    @Resource
    private IProblemRecordService problemRecordService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody ProblemRecord problemRecord) {
        return problemRecordService.saveOrUpdate(problemRecord);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return problemRecordService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return problemRecordService.removeByIds(ids);
    }

    @GetMapping
    public List<ProblemRecord> findAll() {
        return problemRecordService.list();
    }

    @GetMapping("/{id}")
    public ProblemRecord findOne(@PathVariable Integer id) {
        return problemRecordService.getById(id);
    }

    @GetMapping("/page")
    public Page<ProblemRecord> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<ProblemRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return problemRecordService.page(new Page<>(pageNum, pageSize));
    }
}

