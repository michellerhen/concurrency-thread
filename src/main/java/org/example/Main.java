package org.example;

import org.example.benchmark.*;
import org.example.model.BenchmarkResult;
import org.example.model.Order;
import org.example.report.ConsoleReportGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        ConsoleReportGenerator reporter = new ConsoleReportGenerator();
        BenchmarkRunner runner = new BenchmarkRunner();

        reporter.printHeader();

        // Test settings
        int[] workloadSizes = {100, 500, 1000};
        String[] workloadTypes = {"IO", "CPU", "Mixed"};

        List<BenchmarkResult> allResults = new ArrayList<>();

        // For each combination of size and workload type
        for (String workloadType : workloadTypes) {
            for (int size : workloadSizes) {
                reporter.printTestInfo(workloadType, size);

                // Generate orders
                List<Order> orders = runner.generateOrders(size);
                Function<Order, Order> processingFunction = runner.getProcessingFunction(workloadType);

                // List of approaches to test
                List<ConcurrencyBenchmark> benchmarks = createBenchmarks();

                // Run each benchmark
                for (int i = 0; i < benchmarks.size(); i++) {
                    ConcurrencyBenchmark benchmark = benchmarks.get(i);

                    reporter.printBenchmarkProgress(benchmark.getName(), i + 1, benchmarks.size());

                    BenchmarkResult result = runner.runBenchmark(
                        benchmark,
                        orders,
                        workloadType,
                        processingFunction
                    );

                    reporter.printBenchmarkResult(result);
                    allResults.add(result);

                    // Cleanup
                    benchmark.shutdown();

                    // Pause between benchmarks
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        // Print comparisons and summary
        reporter.printComparison(allResults);
        reporter.printSummary(allResults);
        reporter.printFooter();
    }

    private static List<ConcurrencyBenchmark> createBenchmarks() {
        int processors = Runtime.getRuntime().availableProcessors();

        List<ConcurrencyBenchmark> benchmarks = new ArrayList<>();
        benchmarks.add(new ExecutorServiceBenchmark(processors));
        benchmarks.add(new ExecutorServiceBenchmark(processors * 2));
        benchmarks.add(new VirtualThreadBenchmark());
        benchmarks.add(new CompletableFutureBenchmark());
        benchmarks.add(new ParallelStreamBenchmark());

        return benchmarks;
    }
}