package com.lzb.demo.infr.plugin.mq.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizebin
 * @since 2022-03-01
 */
@Data
@TableName("domain_event")
public class DomainEventPo {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String topic;

    private String tag;

    private String key;

    private String body;

    /**
     * 0-待发送;1-已发送
     */
    private Integer status;


}
