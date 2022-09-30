package com.example.mapfence.service.impl;

import com.example.mapfence.entity.User;
import com.example.mapfence.mapper.UserMapper;
import com.example.mapfence.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavi
 * @since 2022-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
