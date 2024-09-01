package com.lzb.component.event.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzb.component.event.persistence.DomainEventPo;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizebin
 * @since 2023-09-02
 */
public interface DomainEventPoMapper extends BaseMapper<DomainEventPo> {

    /**
     * 待发送数量
     * @return
     */
    @Select("""
        select count(1) from domain_event where sent = false
    """)
    int countOfWaitSend();

}