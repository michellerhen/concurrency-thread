package org.example.util;

import org.example.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Test data generator for benchmarks
 */
public class OrderGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] CUSTOMER_PREFIXES = {
        "Tech Corp", "Digital Inc", "Cloud Solutions", "Data Systems",
        "Smart Industries", "Future Tech", "Innovation Labs", "Global Trade"
    };

    /**
     * Generates a list of test orders
     */
    public static List<Order> generateOrders(int count) {
        List<Order> orders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String customerName = generateCustomerName(i);
            double amount = generateAmount();
            orders.add(new Order(customerName, amount));
        }
        return orders;
    }

    /**
     * Generates a varied customer name
     */
    private static String generateCustomerName(int index) {
        String prefix = CUSTOMER_PREFIXES[index % CUSTOMER_PREFIXES.length];
        return String.format("%s #%d", prefix, index);
    }

    /**
     * Generates a random order amount between 10 and 10000
     */
    private static double generateAmount() {
        return 10 + RANDOM.nextDouble() * 9990;
    }

    /**
     * Generates orders with fixed amounts (for deterministic tests)
     */
    public static List<Order> generateOrdersWithFixedAmounts(int count, double amount) {
        List<Order> orders = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            orders.add(new Order("Customer-" + i, amount));
        }
        return orders;
    }
}

