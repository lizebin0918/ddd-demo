package com.lzb.demo.common.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lzb
 * @date 2022/7/20
 */
@Data
@TableName(value = "domain_event")
public class DomainEventDO {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "topic")
    private String topic;

    @TableField(value = "tag")
    private String tag;

    /**
     * 分区字段
     */
    @TableField(value = "sharding_key")
    private String shardingKey;

    /**
     * 业务id，用来确保发送消息是唯一的
     */
    @TableField(value = "biz_id")
    private String bizId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "sent")
    private Boolean sent;

    @TableField(value = "msg_id")
    private String msgId;

}