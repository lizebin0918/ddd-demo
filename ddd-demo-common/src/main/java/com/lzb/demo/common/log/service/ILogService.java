package com.lzb.demo.common.log.service;

import com.lzb.demo.common.log.service.dto.OperationLog;

import java.util.Collection;

/**
 * <br/>
 * Created on : 2022-07-26 15:38
 *
 * @author lzb
 */
public interface ILogService {

    /**
     * 保存操作日志
     * @param operationLog
     */
    void save(OperationLog operationLog);

    /**
     * 保存操作日志
     *
     * @param operationLog
     */
    void batchSave(Collection<OperationLog> operationLog);

}
