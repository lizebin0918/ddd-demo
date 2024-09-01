package com.lzb.component.aggregate;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzb.component.event.DomainEvent;
import com.lzb.component.exception.IllegalVersionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * <p>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@Slf4j
@Getter
@SuperBuilder(toBuilder = true)
@Setter(AccessLevel.PACKAGE)
public abstract class BaseAggregation<R extends BaseAggregation<R, I>, I> extends BaseEntity<I> {

    /**
     * 领域事件
     */
    protected final transient Queue<DomainEvent> events = new LinkedList<>();

    /**
     * 订单的操作日志
     * 订单的操作日志(这里会有风险-类型不安全，暂时只能想到这个方法)
     */
    protected final transient List<OperationLog> logs = new ArrayList<>();

    /**
     * 快照组件
     */
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final transient Snapshot<R, I> snapshot = new Snapshot<>();

    protected BaseAggregation() {}

    /**
     * 切面设置快照
     */
    public void attachSnapshot() {
        snapshot.set((R) this);
    }

    /**
     * 获取快照
     *
     * @return
     */
    public R snapshot() {
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
            throw new IllegalVersionException("快照不存在，在查询聚合的方法上添加注解--@Snapshot");
        }
        if (!Objects.equals(this.version, currentSnapshot.getVersion())) {
            log.error("版本号有误：当前对象版本号={}，快照对象版本号={}", this.version, currentSnapshot.getVersion());
            throw new IllegalVersionException("快照版本号有误，请重试");
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
     * 添加操作日志
     * @param operationLog 操作日志
     * @return boolean
     * @author lizebin
     * @date 2022/11/14
     */
    protected boolean addOperationLog(OperationLog operationLog) {
        return logs.add(operationLog);
    }

}
