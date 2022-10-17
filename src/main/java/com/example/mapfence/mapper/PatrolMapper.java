package com.example.mapfence.mapper;

import com.example.mapfence.entity.Patrol;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xavi
 * @since 2022-10-16
 */
@Mapper
public interface PatrolMapper extends BaseMapper<Patrol> {

    List<Patrol> selectByName(@Param("name") String name);

    List<Patrol> selectByTelephone(@Param("telephone") String telephone);
}
