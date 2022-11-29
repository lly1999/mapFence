package com.example.mapfence.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapfence.component.JwtTokenUtil;
import com.example.mapfence.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.example.mapfence.service.IPatrolStatusService;
import com.example.mapfence.entity.PatrolStatus;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Api(tags = "巡查员状态表的相关接口")
@RestController
@RequestMapping("//patrol-status")
public class PatrolStatusController {

    // 记录日志
    private static final Logger log = LoggerFactory.getLogger(PatrolStatusController.class);

    @Resource
    private IPatrolStatusService patrolStatusService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "app端接口：设置巡查员当日状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "params", value = "{status:}", dataType = "JSON", required = true),
    })
    @PostMapping("/status/set_status")
    public void setStatus(@RequestBody String params, HttpServletRequest servletRequest) {
        // 获取phone
        String phone = jwtTokenUtil.getUserNameFromToken(servletRequest.getHeader(tokenHeader).substring(this.tokenHead.length()));
        // 获取status
        JSONObject jsonObject = JSON.parseObject(params);
        String status = jsonObject.getString("status");
        patrolStatusService.setStatus(phone, Integer.valueOf(status));
    }

    @ApiOperation(value = "web端接口：查询指定巡查员指定日期的状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "patrol_id", value = "巡查员表中的id字段", dataType = "int", required = true),
            @ApiImplicitParam(name = "date", value = "YYYY-MM-DD", dataType = "String", required = true),
    })
    @GetMapping("/status/{patrol_id}/{date}")
    public PatrolStatus selectStatusByMultiId(@PathVariable Integer patrol_id, @PathVariable String date) {
        return patrolStatusService.selectStatusByMultiId(patrol_id, date);
    }

    // 新增或者更新
    @PostMapping
    @ApiOperation(value = "web端接口：新增或更新巡查员状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "patrolStatus", value = "patrolStatus实体", dataType = "PatrolStatus", required = true),
    })
    public Boolean save(@RequestBody PatrolStatus patrolStatus) {
        Integer patrol_id = patrolStatus.getPatrolId();
        // 获取date并转换为String
        LocalDate localDate = patrolStatus.getDate();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(fmt);

        if(patrolStatusService.isRecordExist(patrol_id, date)){
            Integer id = patrolStatusService.selectIdByMultiId(patrol_id, date);
            patrolStatus.setId(id);
            return patrolStatusService.updateById(patrolStatus);
        }
        else
            return patrolStatusService.save(patrolStatus);
    }

    @ApiOperation(value = "测试接口：删除指定ID的巡查员状态记录")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return patrolStatusService.removeById(id);
    }

//    @ApiOperation(value = "批量删除指定ID的记录")
//    @PostMapping("/del/batch")
//    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
//        return patrolStatusService.removeByIds(ids);
//    }

    @ApiOperation(value = "web端接口：查询所有巡查员状态记录")
    @GetMapping
    public List<PatrolStatus> findAll() {
        return patrolStatusService.list();
    }

    @ApiOperation(value = "测试接口：查询指定ID的巡查员状态记录")
    @GetMapping("/{id}")
    public PatrolStatus findOne(@PathVariable Integer id) {
        return patrolStatusService.getById(id);
    }

    @ApiOperation(value = "web端接口：巡查员状态记录的分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "要查询第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页有几条记录", dataType = "int", required = true),
    })
    @GetMapping("/page")
    public Page<PatrolStatus> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<PatrolStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return patrolStatusService.page(new Page<>(pageNum, pageSize));
    }
}

