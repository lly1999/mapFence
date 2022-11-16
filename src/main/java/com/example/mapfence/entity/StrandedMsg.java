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
 * @since 2022-11-07
 */
@Getter
@Setter
  @TableName("stranded_msg")
@ApiModel(value = "StrandedMsg对象", description = "")
public class StrandedMsg implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("消息发送者的标识")
      private String patrolTelephone;

      @ApiModelProperty("消息接收者的标识")
      private String adminTelephone;

      @ApiModelProperty("消息内容")
      private String message;

      @ApiModelProperty("消息发送时间")
      private LocalDateTime createTime;

      @ApiModelProperty("0未读1已读")
      private Boolean isRead;

      @ApiModelProperty("消息发送者")
      private String sender;


}
