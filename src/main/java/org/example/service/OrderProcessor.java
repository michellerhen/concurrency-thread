package org.example.service;

import org.example.model.Order;
import org.example.model.OrderStatus;

import java.util.Random;

public class OrderProcessor {
    private static final Random RANDOM = new Random();

    /**
     * Simulates processing with I/O delay (API calls, database, etc.)
     */
    public Order processWithIODelay(Order order) {
        try {
            // Simulates I/O latency (50-150ms)
            Thread.sleep(50 + RANDOM.nextInt(100));
            return order.withStatus(OrderStatus.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return order.withStatus(OrderStatus.FAILED);
        }
    }

    /**
     * Simulates CPU-intensive processing (complex calculations, encryption, etc.)
     */
    public Order processWithCPUHeavyTask(Order order) {
        // Simulates heavy CPU workload
        long result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += Math.sqrt(i) * Math.sin(i);
        }
        return order.withStatus(OrderStatus.COMPLETED);
    }

    /**
     * Simulates mixed workload (I/O + CPU)
     */
    public Order processWithMixedWorkload(Order order) {
        try {
            // First: short I/O delay
            Thread.sleep(30);

            // Then: CPU processing
            long result = 0;
            for (int i = 0; i < 500_000; i++) {
                result += Math.sqrt(i);
            }

            // One more I/O delay
            Thread.sleep(20);

            return order.withStatus(OrderStatus.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return order.withStatus(OrderStatus.FAILED);
        }
    }

    /**
     * Validates the order (fast operation)
     */
    public boolean validateOrder(Order order) {
        return order.amount() > 0 && order.customerName() != null && !order.customerName().isBlank();
    }
}

