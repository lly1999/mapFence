package com.example.mapfence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-09-26
 */
@Getter
@Setter
  @TableName("patrol_status")
@ApiModel(value = "PatrolStatus对象", description = "")
public class PatrolStatus implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("巡查员ID")
        private Integer patrolId;

      @ApiModelProperty("日期；联合主键")
        private LocalDateTime date;

      @ApiModelProperty("1当日在岗；0当日不在岗")
      private Boolean atWork;

      @ApiModelProperty("1今日休假；0今日不休假")
      private Boolean vacation;

      @ApiModelProperty("1今日补休；0今日不补休")
      private Boolean vacationDefer;

      @ApiModelProperty("上班时间")
      private LocalDateTime onWork;

      @ApiModelProperty("下班时间")
      private LocalDateTime offWork;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
