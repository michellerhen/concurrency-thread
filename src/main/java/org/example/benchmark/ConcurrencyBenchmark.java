package org.example.benchmark;

import org.example.model.Order;

import java.util.List;
import java.util.function.Function;

public interface ConcurrencyBenchmark {
    /**
     * Name of the concurrency approach
     */
    String getName();

    /**
     * Processes a list of orders using the specific concurrency strategy
     */
    List<Order> processOrders(List<Order> orders, Function<Order, Order> processingFunction);

    /**
     * Releases resources (if necessary)
     */
    void shutdown();
}

