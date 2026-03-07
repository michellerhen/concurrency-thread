# 🚀 Concurrency & Thread Management Lab - Java 21

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java](https://img.shields.io/badge/Java-21-blue.svg)

Benchmarking and comparative analysis of different concurrency strategies in Java 21, focused on **parallelism, throughput and resource management**.

## 📋 Objective

Demonstrate practical understanding of:
- ✅ Concurrency and parallelism
- ✅ Virtual Threads (Java 21)
- ✅ ExecutorService (Thread Pools)
- ✅ CompletableFuture
- ✅ Parallel Streams
- ✅ Impact of different workloads (I/O, CPU, Mixed)
- ✅ Performance metrics (time, throughput, memory)

## 🏗️ Project Structure

```
src/main/java/org/example/
├── Main.java                          # Entry point
├── model/
│   ├── Order.java                     # Order model
│   ├── OrderStatus.java               # Order status
│   └── BenchmarkResult.java           # Benchmark result
├── service/
│   └── OrderProcessor.java            # Processing (I/O, CPU, Mixed)
├── benchmark/
│   ├── ConcurrencyBenchmark.java      # Base interface
│   ├── ExecutorServiceBenchmark.java  # Fixed Thread Pool
│   ├── VirtualThreadBenchmark.java    # Java 21 Virtual Threads
│   ├── CompletableFutureBenchmark.java
│   ├── ParallelStreamBenchmark.java
│   └── BenchmarkRunner.java           # Orchestrator
└── report/
    └── ConsoleReportGenerator.java    # Visual reports
```

## 🔬 Test Scenarios

### 1️⃣ I/O-bound Workload
Simulates network/disk latency operations (50-150ms)
- **Ideal for**: Virtual Threads

### 2️⃣ CPU-bound Workload
Intensive processing (mathematical calculations)
- **Ideal for**: ExecutorService (pool = cores)

### 3️⃣ Mixed Workload
Combines I/O and CPU (real-world scenarios)
- **Ideal for**: Virtual Threads or CompletableFuture

## 📊 Collected Metrics

| Metric | Description |
|--------|-------------|
| **Execution Time** | Total execution time |
| **Throughput** | Operations per second (ops/s) |
| **Memory Delta** | Memory variation (MB) |
| **Threads Used** | Number of threads used |

## 🚀 How to Run

### Option 1: Quick Script
```bash
./run.sh
```

### Option 2: Maven
```bash
mvn clean compile exec:java
```

### Option 3: Quick Demo
```bash
./run-demo.sh   # Faster execution
```

## 📈 Example Output

```
═══════════════════════════════════════════════════════════════════════════════════════════════════
              🚀 CONCURRENCY & THREAD MANAGEMENT LAB 🚀              
                     Java 21 - Performance Comparison                     
═══════════════════════════════════════════════════════════════════════════════════════════════════

📊 TEST CONFIGURATION
─────────────────────────────────────────────────────────────────────────────────────────────────
  Workload Type       : IO
  Workload Size       : 1,000 orders
  Available Processors: 8 cores
─────────────────────────────────────────────────────────────────────────────────────────────────

⏳ Running [1/5]: ExecutorService (8 threads)...
   ✓ SUCCESS ExecutorService (8 threads)
   ├─ Execution Time: 6,234 ms
   ├─ Throughput:     160.42 ops/s
   ├─ Memory Delta:   45 MB
   └─ Threads Used:   8

...

═══════════════════════════════════════════════════════════════════════════════════════════════════
                          📈 COMPARATIVE ANALYSIS                          
═══════════════════════════════════════════════════════════════════════════════════════════════════

🔬 IO WORKLOAD
─────────────────────────────────────────────────────────────────────────────────────────────────
  Approach                                 Time            Throughput           Memory         
─────────────────────────────────────────────────────────────────────────────────────────────────
  Virtual Threads                          1,234 ms 🏆     810.37 ops/s 🏆      23 MB 🏆
  ExecutorService (16 threads)             2,456 ms        407.25 ops/s         38 MB
  CompletableFuture                        3,123 ms        320.15 ops/s         42 MB
```

## 🎯 Expected Conclusions

- **I/O-bound**: Virtual Threads dominate (best throughput, lowest latency)
- **CPU-bound**: Optimized ExecutorService (pool = cores) wins
- **Mixed**: Virtual Threads or CompletableFuture are best
- **Parallel Streams**: Good for fast and simple operations

## 🛠️ Technologies

- **Java 21** (Virtual Threads)
- **Maven** (build tool)
- **JMH-style** benchmarking (custom implementation)

## 📝 Requirements

- Java 21+
- Maven 3.8+

---

**Made with ☕ and Java 21 Virtual Threads**

## 🚀 How to Run

### Prerequisites
- **Java 21+** (for Virtual Threads support) ✅ Verified on your system!
- Maven 3.6+ (optional - bash scripts included as alternative)

### ⚡ Quick Start

```bash
# 1. Build
./build.sh

# 2. Run quick demo (~30 seconds)
./run-demo.sh

# 3. See the results! 🎉
```

### 📊 All Options

#### Option 1: Bash Scripts (Recommended - no Maven required)
```bash
./build.sh              # Build
./run-demo.sh          # Quick demo (30s)
./run.sh               # Full benchmark (5min)
./menu.sh              # Interactive menu
```

#### Option 2: With Maven (if available)
```bash
mvn clean compile                                          # Build
mvn exec:java -Dexec.mainClass="org.example.QuickDemo"    # Quick demo
mvn exec:java -Dexec.mainClass="org.example.Main"         # Full benchmark
mvn package && java -jar target/concurrency-thread-*.jar  # JAR
```

#### Option 3: Examples and Custom Benchmarks
```bash
# Practical concurrency examples
java -cp target/classes org.example.examples.ConcurrencyExamples

# Custom benchmark (Virtual Threads vs ExecutorService)
java -cp target/classes org.example.CustomBenchmarkExample
```

### 🔧 Advanced Options

```bash
# With more memory (for large workloads)
java -Xmx2G -Xms1G -cp target/classes org.example.Main

# With thread pinning analysis
java -Djdk.tracePinnedThreads=full -cp target/classes org.example.Main

# With profiling (JFR)
java -XX:StartFlightRecording=duration=60s,filename=benchmark.jfr \
     -cp target/classes org.example.Main

# With GC logs
java -Xlog:gc* -cp target/classes org.example.Main 2>&1 | tee gc.log
```

## 📈 Expected Output

```
═══════════════════════════════════════════════════════════════════════════════════════════════════
                         🚀 CONCURRENCY & THREAD MANAGEMENT LAB 🚀
                              Java 21 - Performance Comparison
═══════════════════════════════════════════════════════════════════════════════════════════════════

📊 TEST CONFIGURATION
────────────────────────────────────────────────────────────────────────────────────────────────────
  Workload Type       : IO
  Workload Size       : 100 orders
  Available Processors: 8 cores
────────────────────────────────────────────────────────────────────────────────────────────────────

⏳ Running [1/5]: ExecutorService (Fixed Pool: 8)...
   ExecutorService (Fixed Pool: 8) ✓ SUCCESS
   ├─ Execution Time: 1.23 s
   ├─ Throughput:     81.30 ops/s
   ├─ Memory Delta:   15 MB
   └─ Threads Used:   8

⏳ Running [2/5]: ExecutorService (Fixed Pool: 16)...
   ExecutorService (Fixed Pool: 16) ✓ SUCCESS
   ├─ Execution Time: 685 ms
   ├─ Throughput:     145.99 ops/s
   ├─ Memory Delta:   18 MB
   └─ Threads Used:   16

⏳ Running [3/5]: Virtual Threads (Java 21)...
   Virtual Threads (Java 21) ✓ SUCCESS
   ├─ Execution Time: 152 ms
   ├─ Throughput:     657.89 ops/s
   ├─ Memory Delta:   8 MB
   └─ Threads Used:   Virtual (Scalable)

... [more results]

═══════════════════════════════════════════════════════════════════════════════════════════════════
                                   📈 COMPARATIVE ANALYSIS
═══════════════════════════════════════════════════════════════════════════════════════════════════

🔬 IO WORKLOAD
────────────────────────────────────────────────────────────────────────────────────────────────────
  Approach                                 Time            Throughput           Memory
────────────────────────────────────────────────────────────────────────────────────────────────────
  ExecutorService (Fixed Pool: 8)         1.23 s          81.30 ops/s          15 MB
  ExecutorService (Fixed Pool: 16)        685 ms          145.99 ops/s         18 MB
  Virtual Threads (Java 21)               152 ms   🏆     657.89 ops/s   🏆    8 MB     🏆
  CompletableFuture                       712 ms          140.45 ops/s         16 MB
  Parallel Stream                         1.18 s          84.75 ops/s          14 MB

  Performance vs Fastest:
  • ExecutorService (Fixed Pool: 8)         709.2% slower
  • ExecutorService (Fixed Pool: 16)        350.7% slower
  • CompletableFuture                       368.4% slower
  • Parallel Stream                         676.3% slower

... [more analyses]

═══════════════════════════════════════════════════════════════════════════════════════════════════
                                 🎯 SUMMARY & RECOMMENDATIONS
═══════════════════════════════════════════════════════════════════════════════════════════════════

  Best Approach by Workload Type:

  IO        → Virtual Threads (Java 21) (152 ms)
  CPU       → ExecutorService (Fixed Pool: 8) (2.45 s)
  MIXED     → Virtual Threads (Java 21) (678 ms)

  💡 Recommendations:

  ✓ I/O-bound tasks:
    Virtual Threads are ideal - they scale to thousands of concurrent operations
    with low memory overhead.

  ✓ CPU-bound tasks:
    ExecutorService with a fixed thread pool (number of cores) delivers better
    performance by avoiding excessive context switching.

  ✓ Mixed workloads:
    Virtual Threads or CompletableFuture offer a good balance between
    parallelism and resource management.

  ⚠ Parallel Streams:
    Best for fast and simple operations. Shares the common ForkJoinPool,
    which may cause contention in larger applications.

═══════════════════════════════════════════════════════════════════════════════════════════════════
                              Benchmark completed successfully! 🎉
═══════════════════════════════════════════════════════════════════════════════════════════════════
```

## 🎓 Learnings & Insights

### Virtual Threads (Java 21) 🌟
- **Advantage**: Excellent for I/O-bound, scales massively
- **Disadvantage**: Minimal but present overhead in pure CPU-bound
- **Use case**: REST APIs, database calls, microservices

### ExecutorService
- **Advantage**: Maximum control, predictable, great for CPU-bound
- **Disadvantage**: Manual pool management, does not scale well for massive I/O
- **Use case**: Parallel data processing, mathematical calculations

### CompletableFuture
- **Advantage**: Modern API, elegant composition of operations
- **Disadvantage**: Shares common ForkJoinPool (may cause contention)
- **Use case**: Async pipelines, orchestration of multiple operations

### Parallel Streams
- **Advantage**: Simple declarative API, easy to use
- **Disadvantage**: Less control, shares global thread pool
- **Use case**: Simple transformations on large collections

## 🔧 Test Settings

The tests run the following combinations:

| Workload Size | Workload Types |
|---------------|----------------|
| 100 orders    | I/O, CPU, Mixed |
| 500 orders    | I/O, CPU, Mixed |
| 1000 orders   | I/O, CPU, Mixed |

Total: **45 benchmarks** (5 strategies × 3 types × 3 sizes)

## 📝 Technical Notes

### Warm-up
Each benchmark runs a 10-operation warm-up before the actual measurement, ensuring the JVM is optimized.

### GC and Memory
- `System.gc()` is called before each benchmark
- Memory is measured before and after each execution
- Results are in MB (memory delta)

### Thread Counting
- ExecutorService: configured pool size
- Virtual Threads: marked as "Virtual (Scalable)"
- CompletableFuture/Parallel Stream: number of available cores

## 🚧 Possible Improvements

- [ ] Add JMH (Java Microbenchmark Harness) for greater precision
- [ ] Export results to CSV/JSON
- [ ] Charts with JFreeChart
- [ ] Real-time CPU monitoring
- [ ] Contention tests (multiple simultaneous benchmarks)
- [ ] Comparison with Kotlin Coroutines
- [ ] WebFlux vs Virtual Threads

## 📚 References

- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [Java Concurrency in Practice](https://jcip.net/)
- [Project Loom](https://wiki.openjdk.org/display/loom)
- [ExecutorService Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ExecutorService.html)

## 📖 Documentation

| File | Description |
|------|-------------|
| **README.md** | Project overview (this file) |
| **QUICKSTART.md** | Quick start guide with all commands |
| **CONFIGURATION.md** | How to customize workloads and settings |
| **ANALYSIS.md** | Detailed analysis of results and insights |
| **TROUBLESHOOTING.md** | Common troubleshooting |

### Execution Scripts

| Script | Purpose |
|--------|---------|
| `./build.sh` | Compiles the project |
| `./run-demo.sh` | Runs quick demo (~30s) |
| `./run.sh` | Runs full benchmark (~5min) |
| `./menu.sh` | Interactive menu with all options |

## 👤 Author

Michelle Henriques - Concurrency & Thread Management Lab

---

⭐ **Tip**: Run with different workload sizes to see how each strategy scales!
