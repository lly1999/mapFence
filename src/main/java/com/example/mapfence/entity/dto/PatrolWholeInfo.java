package com.example.mapfence.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xavi
 * @since 2022-10-14
 */
@Getter
@Setter
@ApiModel(value = "PatrolWholeInfo对象", description = "")
public class PatrolWholeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("巡查员ID")
    private Integer patrol_id;

    @ApiModelProperty("巡查员姓名")
    private String name;

    @ApiModelProperty("巡查员所属单位")
    private String department;

    @ApiModelProperty("巡查员所属类别：执法人员、协管人员")
    private String identity;

    @ApiModelProperty("巡查员电话号码")
    private String telephone;

    @ApiModelProperty("巡查员的微信号")
    private String wechat;

    @ApiModelProperty("巡查员所属辖区;是否与fence相关联？")
    private Integer relatedRegion;

    @ApiModelProperty("巡查员所负责的任务")
    private String task;

    @ApiModelProperty("1当日在岗；0当日不在岗")
    private Boolean atWork;

    @ApiModelProperty("1当日休假；0当日不休假")
    private Boolean vacation;

    @ApiModelProperty("1当日补休；0当日不补休")
    private Boolean vacationDefer;

    @ApiModelProperty("上班时间")
    private LocalDateTime onWork;

    @ApiModelProperty("下班时间")
    private LocalDateTime offWork;

    @ApiModelProperty("巡查员的坐标")
    private String location;
}
