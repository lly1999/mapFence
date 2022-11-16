package com.example.mapfence.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录参数
 */
@Getter
@Setter
public class UserLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
    @ApiModelProperty(value = "密码", required = true)
    private String password;


}
