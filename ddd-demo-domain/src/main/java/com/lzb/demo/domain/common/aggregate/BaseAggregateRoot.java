package com.lzb.demo.domain.common.aggregate;

import com.lzb.demo.common.exception.IllegalVersionException;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * https://cloud.tencent.com/developer/article/1833609
 * 聚合根基类，包含共用属性和方法<br/>
 *
 * 注意：
 * 每次update之后，应该把快照置空，表示更新过了，如果在同一个线程里面，同一个聚合根更新两次，第二次更新应该报错
 *
 * 解决：
 * 采用ThreadLocal缓存快照，每次update的时候判断，当前对象和快照的version是否一致，不一致直接报错...
 *
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@SuperBuilder
public abstract class BaseAggregateRoot<K extends EntityId> {

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
     * 快照组件
     */
    private final Snapshot<K> snapshot = new Snapshot<>();

    /**
     * 生成快照
     * 设置版本号
     */
    public void loadSnapshot() {
        snapshot.set(this);
    }

    /**
     * 获取快照
     * @return
     */
    public BaseAggregateRoot<K> getSnapshot() {
        return snapshot.get();
    }

    /**
     * 清空快照
     */
    public void unloadSnapshot() {
        snapshot.remove();
    }

    /**
     * 检查当前对象和快照版本，如果抛异常，表示当前线程获取两次聚合根，并且做了一次更新，版本号发生变化
     * @throws IllegalVersionException
     */
    public void checkVersion() throws IllegalVersionException {
        if (Objects.isNull(this.snapshot.get()) || this.version != this.snapshot.get().getVersion()) {
            throw new IllegalVersionException("快照版本号发生变更");
        }
    }

    /**
     * (领域事件)入队
     * @param event
     */
    protected void addEvent(DomainEvent event) {
        events.add(event);
    }

}
