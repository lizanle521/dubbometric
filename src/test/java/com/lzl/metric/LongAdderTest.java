package com.lzl.metric;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lizanle
 * @Date 2019/3/19 11:22
 */
@State(Scope.Benchmark)
public class LongAdderTest {
    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public Object testMetricsLongAdderPerformance() {
        final com.alibaba.metrics.LongAdder count = new com.alibaba.metrics.LongAdder();
        count.add(1);
        return count;
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public Object testAtomicLongPerformance() {
        final AtomicLong count = new AtomicLong();
        count.addAndGet(1);
        return count;
    }


//    @Benchmark
//    public Object testJDKLongAdderPerformance() {
//        final LongAdder count = new LongAdder();
//        count.add(1);
//        return count;
//    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + LongAdderTest.class.getSimpleName() + ".*")
                .warmupIterations(3)
                .measurementIterations(5)
                .threads(32)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
