package com.example.mapfence.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginV2Param {
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "密码", required = true)
    private String password;


}
