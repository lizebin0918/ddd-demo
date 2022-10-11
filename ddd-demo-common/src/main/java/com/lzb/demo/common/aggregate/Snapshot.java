package com.lzb.demo.common.aggregate;

import org.apache.commons.lang3.SerializationUtils;

/**
 * <br/>
 * Created on : 2022-04-16 16:04
 *
 * @author lizebin
 */
public class Snapshot<R extends BaseAggregate<R>> {

    /**
     * 缓存聚合根快照（不能声明成static，会导致内存异常）
     */
    private final ThreadLocal<R> context = new ThreadLocal<>();

    /**
     * 设置快照
     */
    @SuppressWarnings("unchecked")
    public void set(R root) {
        context.remove();
        context.set(SerializationUtils.clone(root));
    }

    /**
     * 获取快照
     * @return
     */
    public R get() {
        return context.get();
    }

    /**
     * 移除快照
     */
    public void remove() {
        context.remove();
    }

}
