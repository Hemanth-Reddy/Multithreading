package com.hemanth.multithreading.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * <p>Description of the class.</p>
 *
 * <p>Author: Hemanth Nagireddy</p>
 * <p>Date: 4/10/2025</p>
 */
@Service
public class CustomerService {

    private final Object lock = new Object();
    private int accessCounter = 0;

    @Async("taskExecutor")
    public CompletableFuture<String> getCustomerProfile(String userId) throws InterruptedException {
        Thread.sleep(1000); // simulate delay
        incrementCounter("Profile");
        return CompletableFuture.completedFuture("Profile for " + userId);
    }

    @Async("taskExecutor")
    public CompletableFuture<String> getTransactionHistory(String userId) throws InterruptedException {
        Thread.sleep(0); // simulate delay
        incrementCounter("Transaction");
        return CompletableFuture.completedFuture("Transaction history for " + userId);
    }

    private void incrementCounter(String source) {
        synchronized (lock) {
            accessCounter++;
            System.out.println(Thread.currentThread().getName() + " updated counter to " + accessCounter + " from " + source);
        }
    }

    public int getAccessCounter() {
        return accessCounter;
    }
}
