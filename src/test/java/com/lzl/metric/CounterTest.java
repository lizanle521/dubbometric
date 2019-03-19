package com.lzl.metric;

import com.alibaba.metrics.Counter;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author lizanle
 * @Date 2019/3/19 10:17
 */

public class CounterTest {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        static Counter hello = MetricManager.getCounter("test", MetricName.build("test.my.counter"));
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(CounterTest.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .build();
        new Runner(op).run();
        System.out.println(BenchmarkState.hello.getCount());
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public void counter_inc(BenchmarkState state){
        state.hello.inc();
        /**
         * Benchmark                 Mode  Cnt         Score        Error  Units
         CounterTest.counter_inc  thrpt   30  16459517.157 ± 155035.839  ops/s
         CounterTest.counter_inc   avgt   30        ≈ 10⁻⁷                s/op
         */
    }
}
