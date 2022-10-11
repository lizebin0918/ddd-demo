package com.lzb.demo.common.log.service.dto;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.common.log.common.OperationType;
import com.lzb.demo.common.log.repository.entity.OrderOperationLogDo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

/**
 * <br/>
 * Created on : 2022-07-26 16:00
 *
 * @author lzb
 */
@Slf4j
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {

    /**
     * 订单号
     */
    @NonNull
    private Long orderId;

    /**
     * 操作人id
     */
    @NonNull
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作类型
     */
    private OperationType type;

    /**
     * 备注
     */
    private String remark;

    /**
     * (JSON)备注扩展
     */
    private String remarkExtend;

    private LocalDateTime operationTime;

    public OrderOperationLogDo toDo() {
        OrderOperationLogDo orderOperationLogDo = new OrderOperationLogDo();
        orderOperationLogDo.setOrderId(orderId);
        orderOperationLogDo.setOperatorId(operatorId);
        orderOperationLogDo.setType(type);
        orderOperationLogDo.setRemark(remark);
        orderOperationLogDo.setRemarkExtend(remarkExtend);
        orderOperationLogDo.setOperationTime(operationTime);
        return orderOperationLogDo;
    }

    public static OperationLog toVO(OrderOperationLogDo logDo, String operationName) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOrderId(logDo.getOrderId());
        operationLog.setOperatorId(logDo.getOperatorId());
        operationLog.setOperatorName(operationName);
        operationLog.setType(logDo.getType());
        operationLog.setRemark(logDo.getRemark());
        operationLog.setRemarkExtend(logDo.getRemarkExtend());
        operationLog.setOperationTime(logDo.getOperationTime());
        if (operationLog.getOperationTime() == null) {
            operationLog.setOperationTime(logDo.getAddTime());
        }
        return operationLog;


    }


}
