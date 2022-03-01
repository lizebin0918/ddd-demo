package com.lzb.demo.infr.plugin.mq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzb.demo.infr.plugin.mq.po.DomainEventPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizebin
 * @since 2022-03-01
 */
@Mapper
public interface DomainEventMapper extends BaseMapper<DomainEventPo> {

}
