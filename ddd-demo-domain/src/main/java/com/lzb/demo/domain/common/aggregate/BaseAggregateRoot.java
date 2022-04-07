package com.lzb.demo.domain.common.aggregate;

import com.google.gson.Gson;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.Optional;

/**
 * https://cloud.tencent.com/developer/article/1833609
 * 聚合根基类，包含共用属性和方法<br/>
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@SuperBuilder
public abstract class BaseAggregateRoot<K extends EntityId> {

    private static final Gson GSON = new Gson();

    @NonNull
    protected Integer version;

    @Getter
    @NonNull
    protected K id;

    /**
     * 缓存聚合根快照（不能声明成static，static会导致内存异常）
     */
    private final ThreadLocal<BaseAggregateRoot<K>> AGGREGATE_ROOT_CONTEXT = new ThreadLocal<>();

    /**
     * 领域事件
     */
    private final LinkedList<DomainEvent> events = new LinkedList<>();

    /**
     * 生成快照
     * 设置版本号
     */
    @SuppressWarnings("unchecked")
    public void initSnapshot() {
        String jsonString = GSON.toJson(this);
        BaseAggregateRoot<K> snapshot = GSON.fromJson(jsonString, this.getClass());
        AGGREGATE_ROOT_CONTEXT.set(snapshot);
    }

    /**
     * 刷新快照
     */
    public void refreshSnapshot() {
        clearSnapshot();
        initSnapshot();
    }

    /**
     * 清空快照
     */
    public void clearSnapshot() {
        AGGREGATE_ROOT_CONTEXT.remove();
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

    /**
     * 获取版本号
     * @return
     */
    public int getSnapshotVersion() {
        return getSnapshot().version;
    }

    /**
     * 获取快照
     * @return
     */
    public BaseAggregateRoot<K> getSnapshot() {
        return AGGREGATE_ROOT_CONTEXT.get();
    }

}
