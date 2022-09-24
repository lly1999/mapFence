package com.example.mapfence.mapper;

import com.example.mapfence.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("Select * from user")
    List<User> findAll();
}
