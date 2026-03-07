package org.example.report;

import org.example.model.BenchmarkResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleReportGenerator {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_MAGENTA = "\u001B[35m";

    public void printHeader() {
        printSeparator("═");
        System.out.println(ANSI_BOLD + ANSI_CYAN +
            centerText("🚀 CONCURRENCY & THREAD MANAGEMENT LAB 🚀", 100) + ANSI_RESET);
        System.out.println(ANSI_CYAN +
            centerText("Java 21 - Performance Comparison", 100) + ANSI_RESET);
        printSeparator("═");
        System.out.println();
    }

    public void printTestInfo(String workloadType, int workloadSize) {
        System.out.println(ANSI_BOLD + ANSI_YELLOW + "📊 TEST CONFIGURATION" + ANSI_RESET);
        printSeparator("─");
        System.out.printf("  %-20s: %s%s%s%n", "Workload Type", ANSI_GREEN, workloadType.toUpperCase(), ANSI_RESET);
        System.out.printf("  %-20s: %s%,d%s orders%n", "Workload Size", ANSI_GREEN, workloadSize, ANSI_RESET);
        System.out.printf("  %-20s: %s%d%s cores%n", "Available Processors", ANSI_GREEN,
            Runtime.getRuntime().availableProcessors(), ANSI_RESET);
        printSeparator("─");
        System.out.println();
    }

    public void printBenchmarkProgress(String approachName, int current, int total) {
        System.out.printf(ANSI_BLUE + "⏳ Running [%d/%d]: %s%s...%n",
            current, total, approachName, ANSI_RESET);
    }

    public void printBenchmarkResult(BenchmarkResult result) {
        String status = result.success() ? ANSI_GREEN + "✓ SUCCESS" : ANSI_RED + "✗ FAILED";
        System.out.printf(ANSI_BOLD + "   %s %s%s%n", result.approach(), status, ANSI_RESET);
        System.out.printf("   ├─ Execution Time: %s%s%s%n",
            ANSI_YELLOW, result.formatExecutionTime(), ANSI_RESET);
        System.out.printf("   ├─ Throughput:     %s%s%s%n",
            ANSI_GREEN, result.formatThroughput(), ANSI_RESET);
        System.out.printf("   ├─ Memory Delta:   %s%,d MB%s%n",
            ANSI_CYAN, result.peakMemoryMB(), ANSI_RESET);

        String threadsInfo = result.threadsUsed() == -1 ? "Virtual (Scalable)" : String.valueOf(result.threadsUsed());
        System.out.printf("   └─ Threads Used:   %s%s%s%n",
            ANSI_MAGENTA, threadsInfo, ANSI_RESET);
        System.out.println();
    }

    public void printComparison(List<BenchmarkResult> results) {
        System.out.println();
        printSeparator("═");
        System.out.println(ANSI_BOLD + ANSI_CYAN +
            centerText("📈 COMPARATIVE ANALYSIS", 100) + ANSI_RESET);
        printSeparator("═");
        System.out.println();

        // Group by workload type
        Map<String, List<BenchmarkResult>> groupedResults = results.stream()
            .collect(Collectors.groupingBy(BenchmarkResult::workloadType));

        groupedResults.forEach((workloadType, workloadResults) -> {
            printWorkloadComparison(workloadType, workloadResults);
        });
    }

    private void printWorkloadComparison(String workloadType, List<BenchmarkResult> results) {
        System.out.println(ANSI_BOLD + ANSI_YELLOW + "🔬 " + workloadType.toUpperCase() +
            " WORKLOAD" + ANSI_RESET);
        printSeparator("─");

        // Find best in each category
        BenchmarkResult fastest = results.stream()
            .filter(BenchmarkResult::success)
            .min((r1, r2) -> Long.compare(r1.executionTimeMs(), r2.executionTimeMs()))
            .orElse(null);

        BenchmarkResult highestThroughput = results.stream()
            .filter(BenchmarkResult::success)
            .max((r1, r2) -> Double.compare(r1.throughput(), r2.throughput()))
            .orElse(null);

        BenchmarkResult lowestMemory = results.stream()
            .filter(BenchmarkResult::success)
            .min((r1, r2) -> Long.compare(r1.peakMemoryMB(), r2.peakMemoryMB()))
            .orElse(null);

        // Print table header
        System.out.printf("  %-40s %-15s %-20s %-15s%n",
            "Approach", "Time", "Throughput", "Memory");
        printSeparator("─");

        // Print each result with indicators for best performers
        for (BenchmarkResult result : results) {
            if (!result.success()) continue;

            String timeMarker = result.equals(fastest) ? " 🏆" : "";
            String throughputMarker = result.equals(highestThroughput) ? " 🏆" : "";
            String memoryMarker = result.equals(lowestMemory) ? " 🏆" : "";

            System.out.printf("  %-40s %s%-13s%s%-2s %s%-18s%s%-2s %s%-13s%s%-2s%n",
                result.approach(),
                ANSI_YELLOW, result.formatExecutionTime(), ANSI_RESET, timeMarker,
                ANSI_GREEN, result.formatThroughput(), ANSI_RESET, throughputMarker,
                ANSI_CYAN, result.peakMemoryMB() + " MB", ANSI_RESET, memoryMarker
            );
        }

        System.out.println();
        printPerformanceGains(results, fastest);
        System.out.println();
    }

    private void printPerformanceGains(List<BenchmarkResult> results, BenchmarkResult baseline) {
        if (baseline == null) return;

        System.out.println(ANSI_BOLD + "  Performance vs Fastest:" + ANSI_RESET);

        results.stream()
            .filter(BenchmarkResult::success)
            .filter(r -> !r.equals(baseline))
            .forEach(result -> {
                double percentSlower = ((result.executionTimeMs() - baseline.executionTimeMs()) * 100.0)
                    / baseline.executionTimeMs();
                String color = percentSlower < 20 ? ANSI_GREEN : percentSlower < 50 ? ANSI_YELLOW : ANSI_RED;
                System.out.printf("  • %-40s %s%.1f%%%s slower%n",
                    result.approach(), color, percentSlower, ANSI_RESET);
            });
    }

    public void printSummary(List<BenchmarkResult> results) {
        System.out.println();
        printSeparator("═");
        System.out.println(ANSI_BOLD + ANSI_CYAN +
            centerText("🎯 SUMMARY & RECOMMENDATIONS", 100) + ANSI_RESET);
        printSeparator("═");
        System.out.println();

        // Best overall by workload type
        Map<String, BenchmarkResult> bestByWorkload = results.stream()
            .filter(BenchmarkResult::success)
            .collect(Collectors.groupingBy(
                BenchmarkResult::workloadType,
                Collectors.collectingAndThen(
                    Collectors.minBy((r1, r2) -> Long.compare(r1.executionTimeMs(), r2.executionTimeMs())),
                    opt -> opt.orElse(null)
                )
            ));

        System.out.println(ANSI_BOLD + "  Best Approach by Workload Type:" + ANSI_RESET);
        System.out.println();

        bestByWorkload.forEach((workloadType, result) -> {
            System.out.printf("  %s%-10s%s → %s%s%s (%s)%n",
                ANSI_YELLOW, workloadType.toUpperCase(), ANSI_RESET,
                ANSI_GREEN + ANSI_BOLD, result.approach(), ANSI_RESET,
                result.formatExecutionTime()
            );
        });

        System.out.println();
        printRecommendations(bestByWorkload);
        System.out.println();
        printSeparator("═");
    }

    private void printRecommendations(Map<String, BenchmarkResult> bestByWorkload) {
        System.out.println(ANSI_BOLD + "  💡 Recommendations:" + ANSI_RESET);
        System.out.println();

        System.out.println("  " + ANSI_GREEN + "✓ I/O-bound tasks:" + ANSI_RESET);
        System.out.println("    Virtual Threads are ideal - they scale to thousands of concurrent operations");
        System.out.println("    with low memory overhead.");
        System.out.println();

        System.out.println("  " + ANSI_GREEN + "✓ CPU-bound tasks:" + ANSI_RESET);
        System.out.println("    ExecutorService with a fixed thread pool (number of cores) delivers better");
        System.out.println("    performance by avoiding excessive context switching.");
        System.out.println();

        System.out.println("  " + ANSI_GREEN + "✓ Mixed workloads:" + ANSI_RESET);
        System.out.println("    Virtual Threads or CompletableFuture offer a good balance between");
        System.out.println("    parallelism and resource management.");
        System.out.println();

        System.out.println("  " + ANSI_YELLOW + "⚠ Parallel Streams:" + ANSI_RESET);
        System.out.println("    Best for fast and simple operations. Shares the common ForkJoinPool,");
        System.out.println("    which may cause contention in larger applications.");
    }

    private void printSeparator(String character) {
        System.out.println(character.repeat(100));
    }

    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    public void printFooter() {
        printSeparator("═");
        System.out.println(ANSI_CYAN + centerText("Benchmark completed successfully! 🎉", 100) + ANSI_RESET);
        printSeparator("═");
    }
}

