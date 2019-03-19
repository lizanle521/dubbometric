package com.lzl.metric;

import com.alibaba.fastjson.JSON;
import com.alibaba.metrics.Histogram;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

/**
 * @author lizanle
 * @Date 2019/3/19 10:17
 */
@State(Scope.Benchmark)
public class HistogramTest {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
          static Histogram histogram = MetricManager.getHistogram("test",MetricName.build("com.test.histogram"));
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(HistogramTest.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .build();
        new Runner(op).run();

    }

    @TearDown(Level.Trial)
    public void check(){
        System.out.println(JSON.toJSONString(BenchmarkState.histogram.getSnapshot()));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public void histogram(BenchmarkState state){

        state.histogram.update(1);
        /**
         HistogramTest.histogram  thrpt   10  5348296.165 ± 60929.871  ops/s
         HistogramTest.histogram   avgt   10       ≈ 10⁻⁶               s/op
         */
    }
}
