package com.lzb.demo.common.repository.base;

import com.lzb.demo.common.helper.TransactionHelper;
import com.lzb.demo.common.log.service.ILogService;
import com.lzb.demo.common.log.service.dto.OperationLog;
import com.lzb.demo.common.repository.event.DomainEvent;
import com.lzb.demo.common.repository.event.DomainEventSender;
import lombok.NonNull;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * 仓储基类，用于通用操作<br/>
 * Created on : 2022-03-01 13:41
 *
 * @author lizebin
 */
public class BaseRepository {

    @Resource
    private TransactionHelper transactionHelper;

    @Resource
    private DomainEventSender sender;

    @Resource
    private ILogService logService;

    public void setTransactionHelper(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setSender(DomainEventSender sender) {
        this.sender = sender;
    }

    public void setLogService(ILogService logService) {
        this.logService = logService;
    }

    public void commit(@NonNull Runnable execute,
                       @NonNull Queue<DomainEvent> events,
                       @NonNull List<OperationLog> logs) {
        transactionHelper.runWithRequired(
                Arrays.asList(
                        execute,
                        () -> logService.batchSave(logs),
                        () -> events.forEach(sender::send)
                )
        );
    }

    @NonNull
    public void commit(@NonNull List<Runnable> executes,
                       @NonNull Queue<DomainEvent> events,
                       @NonNull List<OperationLog> logs) {
        executes.add(() -> logService.batchSave(logs));
        executes.add(() -> events.forEach(sender::send));
        transactionHelper.runWithRequired(executes);
    }

}