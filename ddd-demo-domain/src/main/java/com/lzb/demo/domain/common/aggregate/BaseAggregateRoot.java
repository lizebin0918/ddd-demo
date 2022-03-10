package com.lzb.demo.domain.common.aggregate;

import com.alibaba.fastjson.JSON;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
public abstract class BaseAggregateRoot {

    @Getter
    private BaseAggregateRoot snapshot;


    private final LinkedList<DomainEvent> events = new LinkedList<>();

    /**
     * 版本号
     */
    @Getter
    private int version;

    /**
     * 生成快照
     * 设置版本号
     *
     * @param snapshot
     */
    public void snapshot() {
        String jsonString = JSON.toJSONString(this);
        this.snapshot = JSON.parseObject(jsonString, this.getClass());
        this.version = this.snapshot.version;
    }

    /**
     * (领域事件)出队
     * @return
     */
    public Optional<DomainEvent> popEvent() {
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(events.pop());
    }

    /**
     * (领域事件)入队
     * @param event
     */
    protected void pushEvent(DomainEvent event) {
        events.push(event);
    }

}
