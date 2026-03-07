package org.example.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility for collecting metrics during benchmarks
 */
public class MetricsCollector {
    private final AtomicLong totalOperations = new AtomicLong(0);
    private final AtomicLong successfulOperations = new AtomicLong(0);
    private final AtomicLong failedOperations = new AtomicLong(0);
    private volatile long startTime;
    private volatile long endTime;

    public void reset() {
        totalOperations.set(0);
        successfulOperations.set(0);
        failedOperations.set(0);
        startTime = 0;
        endTime = 0;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void end() {
        endTime = System.currentTimeMillis();
    }

    public void recordSuccess() {
        totalOperations.incrementAndGet();
        successfulOperations.incrementAndGet();
    }

    public void recordFailure() {
        totalOperations.incrementAndGet();
        failedOperations.incrementAndGet();
    }

    public long getTotalOperations() {
        return totalOperations.get();
    }

    public long getSuccessfulOperations() {
        return successfulOperations.get();
    }

    public long getFailedOperations() {
        return failedOperations.get();
    }

    public long getElapsedTimeMs() {
        return endTime - startTime;
    }

    public double getSuccessRate() {
        long total = totalOperations.get();
        return total > 0 ? (successfulOperations.get() * 100.0) / total : 0;
    }

    public double getThroughput() {
        long elapsed = getElapsedTimeMs();
        return elapsed > 0 ? (successfulOperations.get() * 1000.0) / elapsed : 0;
    }
}

