package com.example.mapfence.service;

import com.example.mapfence.entity.User;
import com.example.mapfence.entity.Permission;

import  java.util.List;

/**
 * 登录注册模块Service
 */

public interface AuthService {
    /**
     * 根据用户名获取用户信息
     */
    User getUserByName(String name);

    /**
     * 注册
     */
    User register(User userParam);

    /**
     * 登录
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限
     */
    List<Permission> getPermissionList(Integer userId);
}
