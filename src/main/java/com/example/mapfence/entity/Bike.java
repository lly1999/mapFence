package com.example.mapfence.entity;

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
  @ApiModel(value = "Bike对象", description = "")
public class Bike implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("单车管理员姓名")
      private String name;

      @ApiModelProperty("单车管理员所属公司")
      private String company;

      @ApiModelProperty("单车管理员电话")
      private String telephone;

      @ApiModelProperty("单车管理员微信")
      private String wechat;

      @ApiModelProperty("单车管理员所属街道")
      private Integer relatedRegion;

      @ApiModelProperty("单车管理员当前所在街道")
      private Integer currentRegion;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
