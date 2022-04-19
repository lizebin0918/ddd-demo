package com.lzb.demo.domain.common.aggregate;

import com.google.gson.annotations.Expose;
import com.lzb.demo.common.exception.IllegalVersionException;
import com.lzb.demo.domain.common.event.DomainEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 聚合根基类，包含共用属性和方法<br/>
 *
 * 注意：
 * 每次update之后，应该把快照置空，表示更新过了，如果在同一个线程里面，同一个聚合根更新两次，第二次更新应该报错
 *
 * 解决：
 * 采用ThreadLocal缓存快照，每次update的时候判断，当前对象和快照的version是否一致，不一致直接报错...
 *
 * 参考:
 * https://cloud.tencent.com/developer/article/1833609
 *
 * Created on : 2022-02-24 22:49
 *
 * @author lizebin
 */
@SuperBuilder
public abstract class BaseAggregateRoot<R extends BaseAggregateRoot<R, K>, K extends EntityId> implements Serializable {

    @Getter
    @NonNull
    protected int version;

    @Getter
    @NonNull
    protected K id;

    /**
     * 领域事件
     */
    @Getter
    protected final Collection<DomainEvent> events = new LinkedList<>();

    /**
     * 快照组件:自身无需保存快照，无需序列化、反序列化
     */
    private final transient Snapshot<R> snapshot = new Snapshot<>();

    /**
     * 生成快照
     */
    public void loadSnapshot() {
        snapshot.set((R) this);
    }

    /**
     * 获取快照
     * @return
     */
    public R getSnapshot() {
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
    public void checkForVersion() throws IllegalVersionException {
        R currentSnapshot = this.snapshot.get();
        if (Objects.isNull(currentSnapshot) || this.version != currentSnapshot.getVersion()) {
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
