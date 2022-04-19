package com.lzb.demo.domain.common.aggregate;

import org.apache.commons.lang3.SerializationUtils;

/**
 * <br/>
 * Created on : 2022-04-16 16:04
 *
 * @author lizebin
 */
public class Snapshot<K extends EntityId> {

    /**
     * 反序列化无需通过无参构造函数:OffsetDateTime 通过字符串序列化/反序列化有误
     */
    /*private static final Gson GSON = new Gson();*/

    /**
     * 缓存聚合根快照（不能声明成static，static会导致内存异常）
     */
    private final ThreadLocal<BaseAggregateRoot<K>> context = new ThreadLocal<>();

    /**
     * 设置快照
     */
    @SuppressWarnings("unchecked")
    public void set(BaseAggregateRoot<K> root) {
        context.remove();
        context.set(SerializationUtils.clone(root));
    }

    /**
     * 获取快照
     * @return
     */
    public BaseAggregateRoot<K> get() {
        return context.get();
    }

    /**
     * 移除快照
     */
    public void remove() {
        context.remove();
    }

}
