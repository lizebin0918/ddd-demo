package com.lzb.demo.domain.common.aggregate;

import com.google.gson.Gson;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.LinkedList;

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
    @NonNull
    protected Integer version;

    @Getter
    @NonNull
    protected K id;

    /**
     * 领域事件:下单时间 + 日志相关事件
     */
    @Getter
    protected final Collection<DomainEvent> events = new LinkedList<>();

    /**
     * 生成快照
     * 设置版本号
     *
     * @param snapshot
     */
    @SuppressWarnings("unchecked")
    public void snapshot() {
        String jsonString = GSON.toJson(this);
        this.snapshot = GSON.fromJson(jsonString, this.getClass());
        this.version = this.snapshot.version;
    }

    /**
     * (领域事件)入队
     * @param event
     */
    protected void addEvent(DomainEvent event) {
        events.add(event);
    }

}
