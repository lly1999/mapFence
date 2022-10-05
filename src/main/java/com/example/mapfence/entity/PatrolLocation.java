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
 * @since 2022-10-05
 */
@Getter
@Setter
  @TableName("patrol_location")
@ApiModel(value = "PatrolLocation对象", description = "")
public class PatrolLocation implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        private Integer id;

      @ApiModelProperty("外键；巡查员id")
      private Integer patrolId;

      @ApiModelProperty("记录本次坐标的时间；按输入数据库的时间自动生成")
      private LocalDateTime recordTime;

      @ApiModelProperty("巡查员的坐标")
      private String location;


}
