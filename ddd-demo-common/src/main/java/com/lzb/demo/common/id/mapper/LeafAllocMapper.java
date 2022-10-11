package com.lzb.demo.common.id.mapper;

import com.lzb.demo.common.id.enity.LeafAlloc;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LeafAllocMapper {

    @Select("SELECT biz_tag, max_id, step, update_time FROM leaf_alloc")
    @Results(value = {
            @org.apache.ibatis.annotations.Result(column = "biz_tag", property = "key"),
            @org.apache.ibatis.annotations.Result(column = "max_id", property = "maxId"),
            @org.apache.ibatis.annotations.Result(column = "step", property = "step"),
            @org.apache.ibatis.annotations.Result(column = "update_time", property = "updateTime")
    })
    List<LeafAlloc> getAllLeafAllocs();

    @Select("SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = #{tag}")
    @Results(value = {
            @org.apache.ibatis.annotations.Result(column = "biz_tag", property = "key"),
            @org.apache.ibatis.annotations.Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step")
    })
    LeafAlloc getLeafAlloc(@Param("tag") String tag);

    @Update("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);

    @Update("UPDATE leaf_alloc SET max_id = max_id + #{leafAlloc.step} WHERE biz_tag = #{leafAlloc.key}")
    void updateMaxIdByCustomStep(@Param("leafAlloc") LeafAlloc leafAlloc);

    @Select("SELECT DISTINCT biz_tag FROM leaf_alloc")
    List<String> getAllTags();
}
