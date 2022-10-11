package com.lzb.demo.common.id.impl;


import com.lzb.demo.common.common.ThreadFactoryImpl;
import com.lzb.demo.common.exception.BizException;
import com.lzb.demo.common.id.IdGenerator;
import com.lzb.demo.common.id.enity.*;
import com.lzb.demo.common.id.mapper.LeafAllocMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@Service
public class IdGeneratorImpl implements IdGenerator, ApplicationListener<ContextRefreshedEvent> {

    /**
     * SegmentBuffer中的两个Segment均未从DB中装载时的异常码
     */
    private static final long EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL = -3;
    /**
     * 最大步长不超过100,0000
     */
    private static final int MAX_STEP = 1000000;
    /**
     * 一个Segment维持时间为15分钟
     */
    private static final long SEGMENT_DURATION = 15 * 60 * 1000L;
    private final ExecutorService service = new ThreadPoolExecutor(5, Integer.MAX_VALUE, 60L,
            TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactoryImpl("Thread-Segment-Update-"));
    private volatile boolean initOK = false;
    private final Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    @Value("${leaf.cache.enable:true}")
    private boolean isFlushEnable;

    @Autowired
    LeafAllocMapper leafAllocMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initOK) {
            init();
        }
    }

    public boolean init() {
        log.info("初始化分布式ID服务");
        // 确保加载到kv后才初始化成功
        updateCacheFromDb();
        initOK = true;
        updateCacheFromDbAtEveryMinute();
        return initOK;
    }

    private void updateCacheFromDbAtEveryMinute() {
        ScheduledExecutorService service
                = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryImpl("check-idCache-thread", true));
        if (isFlushEnable) {
            if (log.isDebugEnabled()) {
                log.debug("leaf buff自动刷新启动");
            }
            service.scheduleWithFixedDelay(this::updateCacheFromDb, 60, 60, TimeUnit.SECONDS);
        }
    }


    private void updateCacheFromDb() {
        List<String> persistentTags = leafAllocMapper.getAllTags();
        removeExpireTag(persistentTags);
        addNewTag(persistentTags);
    }

    private void addNewTag(List<String> dbTags) {
        Set<String> cacheTag = cache.keySet();
        for (String tag : dbTags) {
            if (!cacheTag.contains(tag)) {
                SegmentBuffer buffer = SegmentBuffer.newInstance(tag);
                cache.put(tag, buffer);
                log.info("Add tag {} from db to IdCache, SegmentBuffer {}", tag, buffer);
            }
        }
    }

    private void removeExpireTag(List<String> dbTags) {
        Set<String> cacheTag = cache.keySet();

        for (String tag : cacheTag) {
            if (!dbTags.contains(tag)) {
                cache.remove(tag);
                log.info("Remove tag {} from IdCache", tag);
            }
        }
    }

    @Override
    public long get(final String key) {
        if (!initOK) {
            throw new BizException("服务还未初始化完毕");
        }
        if (cache.containsKey(key)) {
            SegmentBuffer buffer = cache.get(key);
            if (!buffer.isInitOk()) {
                synchronized (buffer) {
                    if (!buffer.isInitOk()) {
                        try {
                            updateSegmentFromDb(key, buffer.getCurrent());
                            log.info("Init buffer. Update leafkey {} {} from db", key, buffer.getCurrent());
                            buffer.setInitOk(true);
                        } catch (Exception e) {
                            log.warn("Init buffer {} exception", buffer.getCurrent(), e);
                        }
                    }
                }
            }
            return getIdFromSegmentBuffer(cache.get(key)).getId();
        }
        throw new BizException("id初始化错误");
    }

    public void updateSegmentFromDb(String key, Segment segment) {
        SegmentBuffer buffer = segment.getBuffer();
        LeafAlloc leafAlloc;
        if (!buffer.isInitOk()) {
            leafAlloc = updateMaxIdAndGetLeafAlloc(key);
            buffer.setStep(leafAlloc.getStep());
            buffer.setMinStep(leafAlloc.getStep());//leafAlloc中的step为DB中的step
        } else if (buffer.getUpdateTimestamp() == 0) {
            leafAlloc = updateMaxIdAndGetLeafAlloc(key);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(leafAlloc.getStep());
            buffer.setMinStep(leafAlloc.getStep());//leafAlloc中的step为DB中的step
        } else {
            long duration = System.currentTimeMillis() - buffer.getUpdateTimestamp();
            int nextStep = buffer.getStep();
            if (duration < SEGMENT_DURATION) {
                if (nextStep * 2 > MAX_STEP) {
                    //do nothing
                } else {
                    nextStep = nextStep * 2;
                }
            } else if (duration < SEGMENT_DURATION * 2) {
                //do nothing with nextStep
            } else {
                nextStep = nextStep / 2 >= buffer.getMinStep() ? nextStep / 2 : nextStep;
            }
            log.info("leafKey[{}], step[{}], duration[{}mins], nextStep[{}]", key, buffer.getStep(), String.format("%.2f", ((double) duration / (1000 * 60))), nextStep);
            LeafAlloc temp = new LeafAlloc();
            temp.setKey(key);
            temp.setStep(nextStep);
            leafAlloc = updateMaxIdByCustomStepAndGetLeafAlloc(temp);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(nextStep);
            buffer.setMinStep(leafAlloc.getStep());//leafAlloc的step为DB中的step
        }
        // must set value before set max
        long value = leafAlloc.getMaxId() - buffer.getStep();
        segment.getValue().set(value);
        segment.setMax(leafAlloc.getMaxId());
        segment.setStep(buffer.getStep());
    }

    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) throws RuntimeException{
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        try {
            leafAllocMapper.updateMaxId(tag);
            LeafAlloc leafAlloc = leafAllocMapper.getLeafAlloc(tag);
            transactionManager.commit(status);
            return leafAlloc;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }

    }

    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) throws RuntimeException{
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        LeafAlloc leafAlloc1;
        try {
            leafAllocMapper.updateMaxIdByCustomStep(leafAlloc);
            leafAlloc1 = leafAllocMapper.getLeafAlloc(leafAlloc.getKey());
            transactionManager.commit(status);
            return leafAlloc1;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    public Result getIdFromSegmentBuffer(final SegmentBuffer buffer) {
        while (true) {
            buffer.rLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                if (!buffer.isNextReady() && (segment.getIdle() < 0.9 * segment.getStep()) && buffer.getThreadRunning().compareAndSet(false, true)) {
                    service.execute(() -> {
                        Segment next = buffer.getSegments()[buffer.nextPos()];
                        boolean updateOk = false;
                        try {
                            updateSegmentFromDb(buffer.getKey(), next);
                            updateOk = true;
                            log.info("update segment {} from db {}", buffer.getKey(), next);
                        } catch (Exception e) {
                            log.warn(buffer.getKey() + " updateSegmentFromDb exception", e);
                        } finally {
                            if (updateOk) {
                                buffer.wLock().lock();
                                buffer.setNextReady(true);
                                buffer.getThreadRunning().set(false);
                                buffer.wLock().unlock();
                            } else {
                                buffer.getThreadRunning().set(false);
                            }
                        }
                    });
                }
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    return new Result(value, Status.SUCCESS);
                }
            } finally {
                buffer.rLock().unlock();
            }
            waitAndSleep(buffer);
            buffer.wLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    return new Result(value, Status.SUCCESS);
                }
                if (buffer.isNextReady()) {
                    buffer.switchPos();
                    buffer.setNextReady(false);
                } else {
                    log.error("Both two segments in {} are not ready!", buffer);
                    return new Result(EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL, Status.EXCEPTION);
                }
            } finally {
                buffer.wLock().unlock();
            }
        }
    }

    private void waitAndSleep(SegmentBuffer buffer) {
        int roll = 0;
        while (buffer.getThreadRunning().get()) {
            roll += 1;
            if (roll > 10000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                    break;
                } catch (InterruptedException e) {
                    log.warn("Thread {} Interrupted", Thread.currentThread().getName());
                    break;
                }
            }
        }
    }

    public List<LeafAlloc> getAllLeafAllocs() {
        return leafAllocMapper.getAllLeafAllocs();
    }

    public Map<String, SegmentBuffer> getCache() {
        return cache;
    }

}
