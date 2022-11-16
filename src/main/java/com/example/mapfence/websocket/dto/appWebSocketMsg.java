package com.example.mapfence.websocket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "巡查员与服务器之间的websocket通信格式", description = "")
public class appWebSocketMsg {

    @ApiModelProperty("通信目标：”admin手机号“")
    String adminTelephone;
    @ApiModelProperty("通信内容")
    String message;
}
