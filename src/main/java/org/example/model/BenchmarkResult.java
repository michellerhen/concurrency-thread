package org.example.model;

public record BenchmarkResult(
    String approach,
    int workloadSize,
    String workloadType,
    long executionTimeMs,
    double throughput,
    long peakMemoryMB,
    int threadsUsed,
    boolean success
) {
    public double executionTimeSeconds() {
        return executionTimeMs / 1000.0;
    }

    public String formatThroughput() {
        return String.format("%.2f ops/s", throughput);
    }

    public String formatExecutionTime() {
        if (executionTimeMs < 1000) {
            return executionTimeMs + " ms";
        }
        return String.format("%.2f s", executionTimeSeconds());
    }
}

