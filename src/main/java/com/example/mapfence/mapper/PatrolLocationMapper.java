package com.example.mapfence.mapper;

import com.example.mapfence.entity.PatrolLocation;
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
 * @since 2022-10-13
 */
@Mapper
public interface PatrolLocationMapper extends BaseMapper<PatrolLocation> {

    String selectLatestLocationById(@Param("patrol_id") Integer patrol_id);

    List<String> selectTodayLocations(@Param("patrol_id") Integer patrol_id, @Param("today_start") String today_start,
                                      @Param("today_end") String today_end);
}
