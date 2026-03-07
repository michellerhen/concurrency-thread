package org.example;

import org.example.benchmark.*;
import org.example.model.BenchmarkResult;
import org.example.model.Order;
import org.example.report.ConsoleReportGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Quick version of the benchmark for demonstration
 * Uses smaller workloads for faster execution
 */
public class QuickDemo {
    public static void main(String[] args) {
        ConsoleReportGenerator reporter = new ConsoleReportGenerator();
        BenchmarkRunner runner = new BenchmarkRunner();

        reporter.printHeader();

        // Reduced settings for quick demo
        int[] workloadSizes = {50, 100}; // Smaller for faster execution
        String[] workloadTypes = {"IO", "CPU"}; // Only 2 types

        List<BenchmarkResult> allResults = new ArrayList<>();

        System.out.println("🎯 QUICK DEMO MODE - Running abbreviated benchmarks for faster results\n");

        for (String workloadType : workloadTypes) {
            for (int size : workloadSizes) {
                reporter.printTestInfo(workloadType, size);

                List<Order> orders = runner.generateOrders(size);
                Function<Order, Order> processingFunction = runner.getProcessingFunction(workloadType);

                // Only 3 main strategies for the demo
                List<ConcurrencyBenchmark> benchmarks = createQuickBenchmarks();

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

                    benchmark.shutdown();

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        reporter.printComparison(allResults);
        reporter.printSummary(allResults);
        reporter.printFooter();
    }

    private static List<ConcurrencyBenchmark> createQuickBenchmarks() {
        int processors = Runtime.getRuntime().availableProcessors();

        List<ConcurrencyBenchmark> benchmarks = new ArrayList<>();
        benchmarks.add(new ExecutorServiceBenchmark(processors));
        benchmarks.add(new VirtualThreadBenchmark());
        benchmarks.add(new ParallelStreamBenchmark());

        return benchmarks;
    }
}

