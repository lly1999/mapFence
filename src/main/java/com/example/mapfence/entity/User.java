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
 * @since 2022-09-26
 */
@Getter
@Setter
  @ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("系统用户id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("系统用户名")
      private String name;

      @ApiModelProperty("用户密码")
      private String password;

      @ApiModelProperty("该用户的电话号码")
      private String telephone;

      @ApiModelProperty("角色id;外键")
      private Integer roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
