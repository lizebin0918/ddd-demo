package com.lzb.demo.common.log.service.impl;

import com.lzb.demo.common.log.repository.service.IOrderOperationLogDoService;
import com.lzb.demo.common.log.service.ILogService;
import com.lzb.demo.common.log.service.dto.OperationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created on : 2022-07-26 15:38
 *
 * @author lizebin
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class LogService implements ILogService {

    private final IOrderOperationLogDoService orderOperationLogDoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(OperationLog operationLog) {
        orderOperationLogDoService.save(operationLog.toDo());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchSave(Collection<OperationLog> operationLogs) {
        if (Objects.isNull(operationLogs)) {
            return;
        }
        orderOperationLogDoService.saveBatch(operationLogs.stream().map(OperationLog::toDo).collect(Collectors.toList()));
    }
}
