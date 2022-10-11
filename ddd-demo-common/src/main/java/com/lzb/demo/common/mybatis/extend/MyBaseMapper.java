package com.lzb.demo.common.mybatis.extend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 扩展mapper
 * @param <T>
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 更新实体所有字段，add_time/update_time属于数据库维护的字段，没有业务含义，不映射到Do
     *
     * @param entity
     * @return
     */
    boolean updateAllFieldsById(@Param(Constants.ENTITY) T entity);

}
