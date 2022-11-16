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
 * @since 2022-11-08
 */
@Getter
@Setter
  @TableName("bike_location")
@ApiModel(value = "BikeLocation对象", description = "")
public class BikeLocation implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("外键：bike表id")
      private Integer bikeId;

      @ApiModelProperty("单车管理员坐标")
      private String location;

      @ApiModelProperty("记录时间")
      private LocalDateTime recordTime;

      @ApiModelProperty("单车管理员所属公司")
      private String company;
}
