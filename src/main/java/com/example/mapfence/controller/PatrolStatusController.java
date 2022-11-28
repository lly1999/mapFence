package com.example.mapfence.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapfence.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
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

    @ApiOperation(value = "app端接口：上班打卡功能；提供巡查员电话号，生成其当日状态记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "patrolTelephone", value = "该巡查员的登录电话号", dataType = "String", required = true),
    })
    @PostMapping("/status/clock_in/{patrolTelephone}")
    public void clockIn(@PathVariable String patrolTelephone) {
        Integer patrolId = patrolStatusService.telephone2PatrolId(patrolTelephone);
        // 当前日期和时间
        LocalDate date = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(dateFormatter);

        // 若当天已有记录（在岗/休假）则不会新增记录
        if(!patrolStatusService.selectByPatrolIdAndDate(patrolId, dateString).isEmpty()) {
            log.error("该巡查员当天已有记录，无法再打卡");
        }
        else {
            // 插入记录
            PatrolStatus patrolStatus = new PatrolStatus();
            patrolStatus.setPatrolId(patrolId);
            patrolStatus.setAtWork(true);
            patrolStatus.setDate(date);
            patrolStatus.setOnWork(time);

            patrolStatusService.saveOrUpdate(patrolStatus);
            log.info("巡查员：" + patrolTelephone + "上班打卡成功");
        }
    }

    @ApiOperation(value = "app端接口：下班打卡功能；提供巡查员电话号，在状态记录中更新下班时间")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "patrolTelephone", value = "该巡查员的登录电话号", dataType = "String", required = true),
    })
    @PostMapping("/status/clock_out/{patrolTelephone}")
    public void clockOut(@PathVariable String patrolTelephone) {
        Integer patrolId = patrolStatusService.telephone2PatrolId(patrolTelephone);
        // 当前日期和时间
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(dateFormatter);
        LocalDateTime time = LocalDateTime.now();

        // 数据库操作
        PatrolStatus patrolStatus = patrolStatusService.selectByPatrolIdAndDate(patrolId, dateString).get(0);
        patrolStatus.setOffWork(time);
        patrolStatusService.saveOrUpdate(patrolStatus);
        log.info("巡查员：" + patrolTelephone + "下班打卡成功");
    }

    @ApiOperation(value = "web端接口：设置当日为休假/补休状态；在状态记录中更新休假状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "patrolTelephone", value = "该巡查员的登录电话号", dataType = "String", required = true),
            @ApiImplicitParam(name = "date", value = "补休日期", dataType = "String", required = true),
            @ApiImplicitParam(name = "choice", value = "休假/补休", dataType = "String", required = true),
    })
    @PostMapping("/status/vacation/{patrolTelephone}/{date}/{choice}")
    public void setVacation(@PathVariable String patrolTelephone, @PathVariable String date, @PathVariable String choice) {
        Integer patrolId = patrolStatusService.telephone2PatrolId(patrolTelephone);
        // 查询是否已经有当日记录并且状态为上班，如果是则日志记录
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate _date = LocalDate.parse(date);
        // 指定巡查员的指定日期记录存在，并且at_work为true
        List<PatrolStatus> patrolStatuses = patrolStatusService.selectByPatrolIdAndDate(patrolId, date);
        if((!patrolStatuses.isEmpty()) && patrolStatuses.get(0).getAtWork()) {
            log.error("该巡查员当天在岗！");
        }
        // 如果当天没有在岗，则将当天设为休假状态
        else {
            PatrolStatus patrolStatus = new PatrolStatus();
            patrolStatus.setPatrolId(patrolId);
            patrolStatus.setDate(_date);
            if(choice.equals("休假"))
                patrolStatus.setVacation(true);
            else if(choice.equals("补休"))
                patrolStatus.setVacationDefer(true);

            patrolStatusService.saveOrUpdate(patrolStatus);
            log.info("巡查员:" + patrolTelephone + "在这一天" + date + "设为休假状态");
        }
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

