package com.lzb.demo.common.log.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.demo.common.log.common.OperationType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName(value = "order_operation_log", autoResultMap = true)
public class OrderOperationLogDo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作类型
     */
    private OperationType type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备注扩展
     */
    private String remarkExtend;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 添加时间(兼容处理)
     */
    private LocalDateTime addTime;


}