package com.lzb.component.aggregate;

import org.apache.commons.lang3.SerializationUtils;

/**
 * 1.为了解决的问题--get一次聚合根，只能update一次，update两次会出问题：
 * {
 *  Order order = orderRepository.get(orderId).orElseThrow();
 *  order.cancel(null);
 *  orderRepository.update(order);
 *  order.suspend(null, null);
 *  // 希望这里抛异常，如果不抛异常，这里会把脏数据更新到数据库，就算有了版本号，这段代码就会死循环重试...
 *  orderRepository.update(order);
 * }
 * <br/>
 * 2.这里为什么要用ThreadLocal，而不是一个布尔值变量控制？
 * 防止聚合根异步更新，如果异步更新，会检查快照是否存在，不存在则直接报错
 * 注意：缓存聚合根快照（不能声明成static，会导致内存异常）
 *
 * @author lizebin
 **/
public class Snapshot<R extends BaseAggregation<R, I>, I> {

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
