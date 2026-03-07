package org.example.benchmark;

import org.example.model.Order;
import org.example.service.OrderProcessor;

import java.util.List;
import java.util.function.Function;

public class ParallelStreamBenchmark implements ConcurrencyBenchmark {
    private final OrderProcessor processor = new OrderProcessor();

    @Override
    public String getName() {
        return "Parallel Stream";
    }

    @Override
    public List<Order> processOrders(List<Order> orders, Function<Order, Order> processingFunction) {
        return orders.parallelStream()
            .map(processingFunction)
            .toList();
    }

    @Override
    public void shutdown() {
        // Parallel Stream uses ForkJoinPool.commonPool(), no shutdown needed
    }
}

