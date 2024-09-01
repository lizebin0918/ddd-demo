package com.lzb.component.event.persistence;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.component.event.typehandler.ContentTypeHandler;
import com.lzb.component.mybatis.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizebin
 * @since 2023-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("domain_event")
public class DomainEventPo extends BasePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String topic;

    private String tag;

    private String bizId;

    private String key;

    @TableField(typeHandler = ContentTypeHandler.class)
    private String content;

    private Boolean sent;

    private String msgId;

    @Override
    public Serializable id() {
        return id;
    }
}