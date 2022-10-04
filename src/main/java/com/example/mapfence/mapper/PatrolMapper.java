package com.example.mapfence.mapper;

import com.example.mapfence.entity.Patrol;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xavi
 * @since 2022-09-26
 */
@Mapper
public interface PatrolMapper extends BaseMapper<Patrol> {

    List<Patrol> selectByName(@Param("name") String name);
}
