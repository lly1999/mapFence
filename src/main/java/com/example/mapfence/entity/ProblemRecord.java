package com.example.mapfence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
  @TableName("problem_record")
@ApiModel(value = "ProblemRecord对象", description = "")
public class ProblemRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("发布问题的巡查员id")
      private Integer patrolId;

      @ApiModelProperty("问题类型")
      private String type;

      @ApiModelProperty("巡查员的拍照记录，存储URL")
      private String picture;

      @ApiModelProperty("发现问题的区域；外键，关联region表id")
      private Integer region;

      @ApiModelProperty("整改时间，具体日期")
      private LocalDateTime rectifyDate;

      @ApiModelProperty("问题发布日期")
      private LocalDateTime postDate;

      @ApiModelProperty("是否整改；1整改0未整改")
      private Boolean isRectified;

      @ApiModelProperty("问题备注")
      private String comment;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
