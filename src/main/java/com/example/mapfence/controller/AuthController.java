package com.example.mapfence.controller;

import com.example.mapfence.dto.UserLoginParam;
import com.example.mapfence.entity.User;
import com.example.mapfence.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录注册模块")
@RestController
@RequestMapping("//auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Boolean register(@RequestBody User userParam) {
        String username = userParam.getName();
        String password = userParam.getPassword();
        if (username == null || password == null) {
            return false;
        }
        User user = authService.register(userParam);
        if (user == null) {
            return false;
        }
        return true;
    }

    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getName();
        String password = userLoginParam.getPassword();
        Map<String, String> tokenMap = new HashMap<>();
        if (username == null || password == null) {
            tokenMap.put("error", "username or password is null");
            return tokenMap;
        }
        String token = authService.login(username, password);
        if (token == null) {
            return null;
        }
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }
}
