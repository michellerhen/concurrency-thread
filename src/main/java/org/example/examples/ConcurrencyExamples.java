package org.example.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Practical examples of different concurrency strategy usage
 */
public class ConcurrencyExamples {

    /**
     * Example 1: Virtual Threads for multiple HTTP calls
     */
    public static void virtualThreadsHttpExample() throws InterruptedException {
        System.out.println("=== Virtual Threads: HTTP Requests ===\n");

        // Create 1000 virtual threads to simulate 1000 HTTP requests
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();

            for (int i = 0; i < 1000; i++) {
                int requestId = i;
                futures.add(executor.submit(() -> {
                    // Simula chamada HTTP
                    Thread.sleep(100);
                    return "Response from request " + requestId;
                }));
            }

            // Wait for all results
            long start = System.currentTimeMillis();
            for (Future<String> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            long elapsed = System.currentTimeMillis() - start;

            System.out.println("✓ Processed 1000 HTTP requests in " + elapsed + "ms");
            System.out.println("✓ Throughput: " + (1000_000.0 / elapsed) + " req/s\n");
        }
    }

    /**
     * Example 2: ExecutorService for CPU-intensive processing
     */
    public static void executorServiceCpuExample() throws InterruptedException {
        System.out.println("=== ExecutorService: CPU-Heavy Processing ===\n");

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        List<Future<Long>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();

        // Process 100 CPU-intensive tasks
        for (int i = 0; i < 100; i++) {
            futures.add(executor.submit(() -> {
                // Simulate complex calculation
                long result = 0;
                for (int j = 0; j < 1_000_000; j++) {
                    result += Math.sqrt(j);
                }
                return result;
            }));
        }

        // Wait for all results
        for (Future<Long> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("✓ Processed 100 CPU tasks in " + elapsed + "ms");
        System.out.println("✓ Thread pool size: " + cores + " (matched to CPU cores)");
        System.out.println("✓ Throughput: " + (100_000.0 / elapsed) + " ops/s\n");
    }

    /**
     * Example 3: CompletableFuture for operation composition
     */
    public static void completableFutureExample() {
        System.out.println("=== CompletableFuture: Pipeline Composition ===\n");

        long start = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = IntStream.range(0, 100)
            .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    // Step 1: Fetch data
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Data-" + i;
                })
                .thenApply(data -> {
                    // Step 2: Process
                    return data.toUpperCase();
                })
                .thenApply(data -> {
                    // Step 3: Validate
                    return "Validated: " + data;
                })
                .exceptionally(ex -> {
                    // Error handling
                    return "Error: " + ex.getMessage();
                }))
            .toList();

        // Wait for all to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("✓ Completed 100 async pipelines in " + elapsed + "ms");
        System.out.println("✓ Each pipeline: fetch → process → validate");
        System.out.println("✓ Clean error handling with exceptionally()\n");
    }

    /**
     * Example 4: Parallel Stream for data transformation
     */
    public static void parallelStreamExample() {
        System.out.println("=== Parallel Stream: Data Transformation ===\n");

        List<Integer> numbers = IntStream.range(0, 10_000).boxed().toList();

        long start = System.currentTimeMillis();

        List<Double> results = numbers.parallelStream()
            .map(n -> Math.sqrt(n))
            .filter(n -> n > 50)
            .map(n -> n * 2)
            .toList();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("✓ Processed 10,000 numbers in " + elapsed + "ms");
        System.out.println("✓ Results: " + results.size() + " values after filtering");
        System.out.println("✓ Simple declarative API\n");
    }

    /**
     * Example 5: Structured Concurrency (Preview in Java 21)
     */
    public static void structuredConcurrencyExample() {
        System.out.println("=== Structured Concurrency (Preview Feature) ===\n");
        System.out.println("⚠️  Requires --enable-preview flag");
        System.out.println("💡 Ensures all subtasks are completed or cancelled together");
        System.out.println("💡 Prevents thread leaks and simplifies error handling\n");

        // Exemplo conceitual (requer --enable-preview)
        System.out.println("""
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                Future<String> user = scope.fork(() -> fetchUser(userId));
                Future<String> order = scope.fork(() -> fetchOrder(orderId));
                
                scope.join();
                scope.throwIfFailed();
                
                return new Response(user.resultNow(), order.resultNow());
            }
        """);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("🎓 CONCURRENCY EXAMPLES - Practical Use Cases\n");
        System.out.println("=".repeat(80) + "\n");

        virtualThreadsHttpExample();
        Thread.sleep(500);

        executorServiceCpuExample();
        Thread.sleep(500);

        completableFutureExample();
        Thread.sleep(500);

        parallelStreamExample();
        Thread.sleep(500);

        structuredConcurrencyExample();

        System.out.println("=".repeat(80));
        System.out.println("\n✅ All examples completed!");
    }
}

