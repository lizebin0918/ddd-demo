package com.lzb.demo.domain.common.aggregate;

import com.google.gson.Gson;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.Optional;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@SuperBuilder
public abstract class BaseAggregateRoot<K extends EntityId> {

    private static final Gson GSON = new Gson();

    @Getter
    protected BaseAggregateRoot<K> snapshot;

    @Getter
    protected int version;

    @Getter
    @NonNull
    protected K id;

    /**
     * 领域事件
     */
    private final LinkedList<DomainEvent> events = new LinkedList<>();

    /**
     * 生成快照
     * 设置版本号
     *
     * @param snapshot
     */
    public void snapshot() {
        String jsonString = GSON.toJson(this);
        this.snapshot = GSON.fromJson(jsonString, this.getClass());
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
