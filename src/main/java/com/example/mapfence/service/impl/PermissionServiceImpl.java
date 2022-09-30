package com.example.mapfence.service.impl;

import com.example.mapfence.entity.Permission;
import com.example.mapfence.mapper.PermissionMapper;
import com.example.mapfence.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
