package com.example.mapfence.mapper;

import com.example.mapfence.entity.StrandedMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xavi
 * @since 2022-11-07
 */
@Mapper
public interface StrandedMsgMapper extends BaseMapper<StrandedMsg> {
    Integer updateReadStatus(@Param("id") Integer id);
}
