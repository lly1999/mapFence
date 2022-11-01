package com.example.mapfence.websocket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "管理员和巡查员之间的实时通信数据载体", description = "")
public class WebSocketMsg {

    @ApiModelProperty("通信目标：”手机号“或者”admin手机号“")
    String target;
    @ApiModelProperty("通信内容")
    String message;
}
