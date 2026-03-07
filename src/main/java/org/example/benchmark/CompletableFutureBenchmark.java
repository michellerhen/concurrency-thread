package org.example.benchmark;

import org.example.model.Order;
import org.example.service.OrderProcessor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompletableFutureBenchmark implements ConcurrencyBenchmark {
    private final OrderProcessor processor = new OrderProcessor();

    @Override
    public String getName() {
        return "CompletableFuture";
    }

    @Override
    public List<Order> processOrders(List<Order> orders, Function<Order, Order> processingFunction) {
        List<CompletableFuture<Order>> futures = orders.stream()
            .map(order -> CompletableFuture.supplyAsync(() -> processingFunction.apply(order)))
            .toList();

        return futures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    @Override
    public void shutdown() {
        // CompletableFuture uses ForkJoinPool.commonPool() by default, no shutdown needed
    }
}

