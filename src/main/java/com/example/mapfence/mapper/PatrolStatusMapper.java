package com.example.mapfence.mapper;

import com.example.mapfence.entity.PatrolStatus;
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
 * @since 2022-10-14
 */
@Mapper
public interface PatrolStatusMapper extends BaseMapper<PatrolStatus> {
    Integer isRecordExist(@Param("patrol_id") Integer patrol_id, @Param("date") String date);

    Integer selectIdByMultiId(@Param("patrol_id") Integer patrol_id, @Param("date") String date);

    List<PatrolStatus> selectAllByDate(@Param("date") String date);

    PatrolStatus selectStatusByMultiId(@Param("patrol_id") Integer patrol_id, @Param("date") String date);

}
