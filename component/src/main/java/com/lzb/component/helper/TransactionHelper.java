package com.lzb.component.helper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
public class TransactionHelper {

	/**
	 * 以传播等级为REQUIRED的方式运行事务
	 * note:如果当前没有事务，则创建一个事务。如果存在则沿用
	 *
	 * @param tasks
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void runWithRequired(Runnable... tasks) {
		Objects.requireNonNull(tasks);
		for (Runnable task : tasks) {
			task.run();
		}
	}

	/**
	 * 以传播等级为REQUIRED的方式运行事务
	 * note:如果当前没有事务，则创建一个事务。如果存在则沿用
	 *
	 * @param tasks
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void runWithRequired(@NonNull Runnable task) {
		Objects.requireNonNull(task);
		task.run();
	}

	/**
	 * note:支持当前事务，如果当前没有事务，就以非事务的方式执行
	 *
	 * @param task
	 */
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public void supports(@NonNull Runnable task) {
		task.run();
	}

	/**
	 * 以传播等级为REQUIRED的方式运行事务
	 *
	 * @param task
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public <T> T runWithRequired(@NonNull Supplier<T> task) {
		return task.get();
	}

	/**
	 * 以传播等级为REQUIRES_NEW的方式运行事务
	 *
	 * @param task
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void runWithRequiresNew(@NonNull Runnable task) {
		task.run();
	}

	/**
	 * 以传播等级为REQUIRES_NEW的方式运行事务
	 *
	 * @param task
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public <T> T runWithRequiresNew(@NonNull Supplier<T> task) {
		return task.get();
	}

	/**
	 * 以传播等级为NESTED的方式运行事务
	 * note:嵌套事务，上一个事务会挂起，当前事务可以单独提交或者回滚。但是外层事务回滚，当前事务也一并回滚
	 *
	 * @param task
	 * @return
	 */
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public <T> T runWithNested(@NonNull Supplier<T> task) {
		return task.get();
	}

	/**
	 * 事务提交后执行
	 * 这里有个特例
	 * @Slf4j
	 * @Service
	 * @RequiredArgsConstructor
	 * public class MyServiceA {
	 *
	 *     private final TransactionHelper transactionHelper;
	 *
	 *     private final MyServiceA myServiceA;
	 *
	 *     @Transactional
	 *     public void test() {
	 *         log.info("runAfterCommit 1 before");
	 *         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
	 *             @Override
	 *             public void afterCommit() {
	 *                 try {
	 *                     log.info("runAfterCommit 1");
	 *                     myServiceA.test2();
	 *                 } catch (Throwable e) {
	 *                     log.error("事务提交后执行后置任务异常", e);
	 *                 }
	 *             }
	 *         });
	 *     }
	 *
	 *     @Transactional
	 *     public void test2() {
	 *         log.info("runAfterCommit 2 before");
	 *         // 如果test2抛异常了，test的事务依然会提交
	 *         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
	 *             @Override
	 *             public void afterCommit() {
	 *                 try {
	 *                 		// 特例在这里：这一句代码不会执行
	 *                     log.info("runAfterCommit 2");
	 *                 } catch (Throwable e) {
	 *                     log.error("事务提交后执行后置任务异常", e);
	 *                 }
	 *             }
	 *         });
	 *     }
	 *
	 * }
	 * @param task 任务
	 * @author lizebin
	 * @date 2022/11/29
	 */
	public void runAfterCommit(@NonNull Runnable task) {
		TransactionHelper proxy = (TransactionHelper) AopContext.currentProxy();
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				try {
					proxy.runWithRequiresNew(task);
				} catch (Throwable e) {
					log.error("事务提交后执行后置任务异常", e);
				}
			}
		});
	}

	/**
	 * 事务提交后异步执行
	 *
	 * @param task 任务
	 * @author lizebin
	 * @date 2022/11/29
	 */
	public void asyncRunAfterCommit(@NonNull Runnable task) {
		runAfterCommit(() -> CompletableFuture.runAsync(task));
	}
}
