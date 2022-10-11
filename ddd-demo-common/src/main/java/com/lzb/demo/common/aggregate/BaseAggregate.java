package com.lzb.demo.common.aggregate;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzb.demo.common.common.exception.IllegalVersionException;
import com.lzb.demo.common.log.common.OperationType;
import com.lzb.demo.common.log.service.dto.OperationLog;
import com.lzb.demo.common.repository.event.DomainEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * <p>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@Slf4j
@Getter
@SuperBuilder
@NoArgsConstructor
public class BaseAggregate<R extends BaseAggregate<R>> implements Serializable {

    @NonNull
    protected Long id;

    @NonNull
    protected Integer version;

    /**
     * 领域事件
     */
    protected final transient Queue<DomainEvent> events = new LinkedList<>();

    /**
     * 快照组件
     */
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final transient Snapshot<R> snapshot = new Snapshot<>();

    /**
     * 操作日志
     * todo 实体的操作也可能会产生log
     */
    protected final List<OperationLog> logs = new ArrayList<>();

    /**
     * 切面设置快照
     */
    public void setSnapshot() {
        snapshot.set((R) this);
    }

    /**
     * 手动设置快照
     *
     * @param aggregate
     */
    public void setSnapshot(R aggregate) {
        snapshot.set(aggregate);
    }

    /**
     * 获取快照
     *
     * @return
     */
    public R getSnapshot() {
        return snapshot.get();
    }

    /**
     * 清空快照
     */
    public void removeSnapshot() {
        snapshot.remove();
    }

    /**
     * 检查当前对象和快照版本，如果抛异常，表示当前线程获取两次聚合根，并且做了一次更新，版本号发生变化
     *
     * @throws IllegalVersionException
     */
    public void checkForVersion() throws IllegalVersionException {
        R currentSnapshot = snapshot.get();
        if (Objects.isNull(currentSnapshot)) {
            throw new IllegalVersionException("快照不存在,请重试");
        }
        if (!Objects.equals(this.version, currentSnapshot.getVersion())) {
            log.error("版本号有误 当前对象版本号 {} 快照对象版本号 {}", this.version, currentSnapshot.getVersion());
            throw new IllegalVersionException("快照版本号有误,请重试");
        }
    }

    /**
     * (领域事件)入队
     *
     * @param event
     */
    protected void addEvent(DomainEvent event) {
        events.add(event);
    }

    /**
     * 添加日志
     *
     * @param operatorId
     * @param remark
     * @param operationType
     * @param operationTime
     * @return
     */
    protected boolean addOperationLog(long operatorId,
                                      String remark,
                                      OperationType operationType,
                                      LocalDateTime operationTime) {
        return logs.add(OperationLog.builder()
                .orderId(id)
                .operatorId(operatorId)
                .type(operationType)
                .remark(remark)
                .operationTime(operationTime)
                .build());
    }

    /**
     * 添加日志
     *
     * @param operatorId
     * @param remark
     * @param remarkExtend (JSON格式)
     * @param operationType
     * @param operationTime
     * @return
     */
    protected boolean addOperationLog(long operatorId,
                                      String remark,
                                      String remarkExtend,
                                      OperationType operationType,
                                      LocalDateTime operationTime) {
        return logs.add(OperationLog.builder()
                .orderId(id)
                .operatorId(operatorId)
                .type(operationType)
                .remark(remark)
                .remarkExtend(remarkExtend)
                .operationTime(operationTime)
                .build());
    }
}
