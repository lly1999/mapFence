package com.example.mapfence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xavi
 * @since 2022-10-16
 */
@Getter
@Setter
  @ApiModel(value = "Patrol对象", description = "")
public class Patrol implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("巡查员ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("巡查员姓名")
      private String name;

      @ApiModelProperty("巡查员所属单位")
      private String department;

      @ApiModelProperty("巡查员所属类别：执法人员、协管人员")
      private String identity;

      @ApiModelProperty("巡查员的职务")
      private String title;

      @ApiModelProperty("巡查员电话号码")
      private String telephone;

      @ApiModelProperty("巡查员的微信号")
      private String wechat;

      @ApiModelProperty("巡查员所属辖区;是否与fence相关联？")
      private Integer relatedRegion;

      @ApiModelProperty("巡查员当前所在辖区;是否与fence相关联？")
      private Integer currentRegion;

      @ApiModelProperty("巡查员所负责的任务")
      private String task;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
