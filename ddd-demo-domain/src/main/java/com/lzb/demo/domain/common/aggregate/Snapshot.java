package com.lzb.demo.domain.common.aggregate;

import com.google.gson.Gson;

/**
 * <br/>
 * Created on : 2022-04-16 16:04
 *
 * @author lizebin
 */
public class Snapshot<K extends EntityId> {

    private static final Gson GSON = new Gson();

    /**
     * 缓存聚合根快照（不能声明成static，static会导致内存异常）
     */
    private final ThreadLocal<BaseAggregateRoot<K>> context = new ThreadLocal<>();

    /**
     * 设置快照
     */

    public void set(BaseAggregateRoot<K> root) {
        context.remove();
        String jsonString = GSON.toJson(root);
        context.set(GSON.fromJson(jsonString, root.getClass()));
    }

    /**
     * 获取快照
     * @return
     */
    public BaseAggregateRoot<K> get() {
        return context.get();
    }

}
