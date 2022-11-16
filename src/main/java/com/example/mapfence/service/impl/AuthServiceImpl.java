package com.example.mapfence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapfence.component.JwtTokenUtil;
import com.example.mapfence.entity.Permission;
import com.example.mapfence.entity.Role;
import com.example.mapfence.entity.User;
import com.example.mapfence.mapper.PermissionMapper;
import com.example.mapfence.mapper.RoleMapper;
import com.example.mapfence.mapper.UserMapper;
import com.example.mapfence.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username);
        List<User> users = userMapper.selectList(wrapper);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User register(User userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        user.setCreateTime(LocalDateTime.now());
        //查询是否有相同用户名的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", user.getName());
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<Permission> getPermissionList(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            LOGGER.warn("invalid userId for " + userId);
            return null;
        }
        Integer roleId = user.getRoleId();
        if (roleId == null) {
            LOGGER.warn("enmpty roleId for userId: " + userId);
            return null;
        }
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            LOGGER.warn("invalid roleId for " + roleId);
            return null;
        }
        Integer permissionId = role.getPermissionId();
        if (permissionId == null) {
            LOGGER.warn("empty permissionId for roleId: " + roleId);
            return null;
        }
        Permission permission = permissionMapper.selectById(permissionId);
        if (permission == null) {
            LOGGER.warn("invalid permissionId for " + permissionId);
            return null;
        }
        List<Permission> res = new ArrayList<>();
        res.add(permission);
        return res;
    }
}
