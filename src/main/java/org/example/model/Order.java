package org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Order(
    String id,
    String customerName,
    double amount,
    LocalDateTime createdAt,
    OrderStatus status
) {
    public Order(String customerName, double amount) {
        this(
            UUID.randomUUID().toString(),
            customerName,
            amount,
            LocalDateTime.now(),
            OrderStatus.PENDING
        );
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(id, customerName, amount, createdAt, newStatus);
    }
}

