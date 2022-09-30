package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.mapfence.service.IPermissionService;
import com.example.mapfence.entity.Permission;

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
@RequestMapping("//permission")
public class PermissionController {

    @Resource
    private IPermissionService permissionService;

    // 新增或者更新
    @PostMapping
    public Boolean save(@RequestBody Permission permission) {
        return permissionService.saveOrUpdate(permission);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return permissionService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return permissionService.removeByIds(ids);
    }

    @GetMapping
    public List<Permission> findAll() {
        return permissionService.list();
    }

    @GetMapping("/{id}")
    public Permission findOne(@PathVariable Integer id) {
        return permissionService.getById(id);
    }

    @GetMapping("/page")
    public Page<Permission> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return permissionService.page(new Page<>(pageNum, pageSize));
    }
}

