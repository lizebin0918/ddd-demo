package com.lzb.demo.common.helper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;


@Component
public class TransactionHelper {

    /**
     * 以传播等级为REQUIRED的方式运行事务
     *
     * @param task
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void runWithRequired(Runnable task) {
        task.run();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void supports(Runnable task) {
        task.run();
    }

    /**
     * 以传播等级为REQUIRED的方式运行事务
     *
     * @param tasks
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void runWithRequired(List<Runnable> tasks) {
        tasks.forEach(Runnable::run);
    }

    /**
     * 以传播等级为REQUIRED的方式运行事务
     *
     * @param task
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> T runWithRequired(Supplier<T> task) {
        return task.get();
    }

    /**
     * 以传播等级为REQUIRES_NEW的方式运行事务
     *
     * @param task
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void runWithRequiresNew(Runnable task) {
        task.run();
    }

    /**
     * 以传播等级为REQUIRES_NEW的方式运行事务
     *
     * @param task
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public <T> T runWithRequiresNew(Supplier<T> task) {
        return task.get();
    }

}
