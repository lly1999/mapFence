package com.example.mapfence.controller;


import com.example.mapfence.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "获取登录用户信息")
@RestController
@RequestMapping("//info")
public class InfoController {
    @Resource
    private InfoService infoService;

    @GetMapping
    @ApiOperation(value = "用户信息")
    public Map<String, String> getInfo() {
        return infoService.getInfo();
    }
}
