package org.example.benchmark;

import org.example.model.Order;
import org.example.service.OrderProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VirtualThreadBenchmark implements ConcurrencyBenchmark {
    private final OrderProcessor processor = new OrderProcessor();
    private final ExecutorService executorService;

    public VirtualThreadBenchmark() {
        // Java 21 Virtual Threads
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public String getName() {
        return "Virtual Threads (Java 21)";
    }

    @Override
    public List<Order> processOrders(List<Order> orders, Function<Order, Order> processingFunction) {
        List<Future<Order>> futures = orders.stream()
            .map(order -> executorService.submit(() -> processingFunction.apply(order)))
            .toList();

        return futures.stream()
            .map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    throw new RuntimeException("Error processing order", e);
                }
            })
            .collect(Collectors.toList());
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}

