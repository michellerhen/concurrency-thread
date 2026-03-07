package org.example;

import org.example.benchmark.*;
import org.example.model.BenchmarkResult;
import org.example.model.Order;
import org.example.report.ConsoleReportGenerator;
import org.example.util.OrderGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Custom example showing how to create your own test scenarios
 */
public class CustomBenchmarkExample {

    public static void main(String[] args) {
        // Scenario: Compare Virtual Threads vs ExecutorService for heavy I/O

        ConsoleReportGenerator reporter = new ConsoleReportGenerator();
        BenchmarkRunner runner = new BenchmarkRunner();

        System.out.println("🎯 CUSTOM BENCHMARK: Virtual Threads vs Traditional Thread Pool\n");
        System.out.println("Scenario: Processing 200 orders with I/O-heavy workload\n");

        // Generate orders
        List<Order> orders = OrderGenerator.generateOrders(200);
        Function<Order, Order> ioProcessing = runner.getProcessingFunction("IO");

        List<BenchmarkResult> results = new ArrayList<>();

        // Test 1: ExecutorService with 8 threads
        System.out.println("📊 Test 1: ExecutorService (8 threads)");
        ConcurrencyBenchmark executor8 = new ExecutorServiceBenchmark(8);
        BenchmarkResult result1 = runner.runBenchmark(executor8, orders, "IO", ioProcessing);
        reporter.printBenchmarkResult(result1);
        results.add(result1);
        executor8.shutdown();

        // Test 2: ExecutorService with 50 threads
        System.out.println("📊 Test 2: ExecutorService (50 threads - over-provisioned)");
        ConcurrencyBenchmark executor50 = new ExecutorServiceBenchmark(50);
        BenchmarkResult result2 = runner.runBenchmark(executor50, orders, "IO", ioProcessing);
        reporter.printBenchmarkResult(result2);
        results.add(result2);
        executor50.shutdown();

        // Test 3: Virtual Threads
        System.out.println("📊 Test 3: Virtual Threads");
        ConcurrencyBenchmark virtualThreads = new VirtualThreadBenchmark();
        BenchmarkResult result3 = runner.runBenchmark(virtualThreads, orders, "IO", ioProcessing);
        reporter.printBenchmarkResult(result3);
        results.add(result3);
        virtualThreads.shutdown();

        // Análise
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📊 ANALYSIS\n");

        BenchmarkResult fastest = results.stream()
            .min((r1, r2) -> Long.compare(r1.executionTimeMs(), r2.executionTimeMs()))
            .orElseThrow();

        System.out.println("🏆 Winner: " + fastest.approach());
        System.out.println("   Time: " + fastest.formatExecutionTime());
        System.out.println("   Throughput: " + fastest.formatThroughput());
        System.out.println("   Memory: " + fastest.peakMemoryMB() + " MB");
        System.out.println();

        System.out.println("💡 Key Insight:");
        if (fastest.approach().contains("Virtual")) {
            System.out.println("   Virtual Threads dominate I/O-bound scenarios because:");
            System.out.println("   • They don't block OS threads during I/O wait");
            System.out.println("   • Can scale to thousands/millions of concurrent operations");
            System.out.println("   • Minimal memory overhead compared to platform threads");
        } else {
            System.out.println("   Traditional thread pools won in this scenario.");
        }

        System.out.println("\n" + "=".repeat(80));
    }
}

