package org.example.benchmark;

import org.example.model.BenchmarkResult;
import org.example.model.Order;
import org.example.service.OrderProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BenchmarkRunner {
    private final OrderProcessor processor = new OrderProcessor();

    public BenchmarkResult runBenchmark(
        ConcurrencyBenchmark benchmark,
        List<Order> orders,
        String workloadType,
        Function<Order, Order> processingFunction
    ) {
        // Warm-up
        if (orders.size() > 10) {
            benchmark.processOrders(orders.subList(0, Math.min(10, orders.size())), processingFunction);
        }

        // Force GC before benchmark
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Collect memory metrics before
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // Run the benchmark
        long startTime = System.currentTimeMillis();

        List<Order> processedOrders;
        boolean success = true;
        try {
            processedOrders = benchmark.processOrders(orders, processingFunction);
        } catch (Exception e) {
            success = false;
            processedOrders = new ArrayList<>();
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Collect memory metrics after
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long peakMemory = (memoryAfter - memoryBefore) / (1024 * 1024); // Convert to MB

        // Calculate throughput (operations per second)
        double throughput = success ? (orders.size() * 1000.0) / executionTime : 0;

        // Threads used (approximation)
        int threadsUsed = estimateThreadsUsed(benchmark);

        return new BenchmarkResult(
            benchmark.getName(),
            orders.size(),
            workloadType,
            executionTime,
            throughput,
            Math.max(0, peakMemory),
            threadsUsed,
            success
        );
    }

    private int estimateThreadsUsed(ConcurrencyBenchmark benchmark) {
        String name = benchmark.getName();
        if (name.contains("Fixed Pool")) {
            // Extract thread count from name
            String[] parts = name.split(":");
            if (parts.length > 1) {
                try {
                    return Integer.parseInt(parts[1].trim().replace(")", ""));
                } catch (NumberFormatException e) {
                    return Runtime.getRuntime().availableProcessors();
                }
            }
        } else if (name.contains("Parallel Stream") || name.contains("CompletableFuture")) {
            return Runtime.getRuntime().availableProcessors();
        } else if (name.contains("Virtual Threads")) {
            return -1; // Virtual threads can scale much higher
        }
        return Runtime.getRuntime().availableProcessors();
    }

    public List<Order> generateOrders(int count) {
        List<Order> orders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            orders.add(new Order("Customer-" + i, 100.0 + (i % 900)));
        }
        return orders;
    }

    public Function<Order, Order> getProcessingFunction(String workloadType) {
        return switch (workloadType.toLowerCase()) {
            case "io" -> processor::processWithIODelay;
            case "cpu" -> processor::processWithCPUHeavyTask;
            case "mixed" -> processor::processWithMixedWorkload;
            default -> throw new IllegalArgumentException("Unknown workload type: " + workloadType);
        };
    }
}

