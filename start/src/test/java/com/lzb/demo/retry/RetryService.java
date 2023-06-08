package com.lzb.demo.retry;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.aop.framework.AopContext;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

    public int test() {
        AtomicInteger counter = new AtomicInteger();
        doTest(counter);
        return counter.get();
    }

    private void doTest(AtomicInteger counter) {
        System.out.println("before doTest");
        try {
            ((RetryService) AopContext.currentProxy()).doTest1(counter);
        } catch (IOException e) {
            // ignore
        }
        System.out.println("after doTest");
    }

    @Retryable(value = IOException.class, maxAttempts = 5)
    public void doTest1(AtomicInteger counter) throws IOException {
        System.out.println(counter.incrementAndGet());
        throw new IOException();
    }

}