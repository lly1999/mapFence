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
@Api(tags = "问题记录表的增删改查与分页查询")
@RestController
@RequestMapping("//problem-record")
public class ProblemRecordController {

    @Resource
    private IProblemRecordService problemRecordService;

    // 新增或者更新
    @ApiOperation(value = "新增一条记录，若ID重复则更新")
    @PostMapping
    public Boolean save(@RequestBody ProblemRecord problemRecord) {
        return problemRecordService.saveOrUpdate(problemRecord);
    }

    @ApiOperation(value = "删除指定ID的记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return problemRecordService.removeById(id);
    }

    @ApiOperation(value = "批量删除指定ID的记录")
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return problemRecordService.removeByIds(ids);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping
    public List<ProblemRecord> findAll() {
        return problemRecordService.list();
    }

    @ApiOperation(value = "查询指定ID的记录")
    @GetMapping("/{id}")
    public ProblemRecord findOne(@PathVariable Integer id) {
        return problemRecordService.getById(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<ProblemRecord> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<ProblemRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return problemRecordService.page(new Page<>(pageNum, pageSize));
    }
}

