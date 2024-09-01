package com.lzb.component.repository;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.lzb.component.aggregate.BaseAggregation;
import com.lzb.component.event.DomainEventSupport;
import com.lzb.component.helper.TransactionHelper;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Setter
public abstract class BaseRepository<R extends BaseAggregation<R, I>, I> implements CommonRepository<R, I>, ApplicationContextAware {

    @Resource
    protected TransactionHelper transactionHelper;

    @Resource
    protected DomainEventSupport domainEventSupport;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 添加聚合根
     *
     * @param aggregate
     * @return 返回主键
     */
    public abstract Supplier<I> doAdd(R aggregate);

    /**
     * 更新聚合根
     *
     * @param aggregate
     * @return
     */
    public abstract Runnable doUpdate(R aggregate);

    protected BaseRepository<R, I> getCurrentProxy() {
        if (Objects.isNull(applicationContext)) {
            return this;
        }
        return (BaseRepository<R, I>) AopContext.currentProxy();
    }

    @Override
    public I add(@NonNull R aggregate) {
        AtomicReference<I> id = new AtomicReference<>();
        Supplier<I> idSupplier = getCurrentProxy().doAdd(aggregate);
        transactionHelper.runWithRequired(() -> {
            id.set(idSupplier.get());
            domainEventSupport.sendEventAfterPersist(aggregate.getEvents());
        });
        return id.get();
    }


    @Override
    public void update(@NonNull R aggregate) {
        Runnable doUpdate = getCurrentProxy().doUpdate(aggregate);
        transactionHelper.runWithRequired(() -> {
            doUpdate.run();
            domainEventSupport.sendEventAfterPersist(aggregate.getEvents());
        });
    }
}