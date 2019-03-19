package com.lzl.metric;

import com.alibaba.fastjson.JSON;
import com.alibaba.metrics.Counter;
import com.alibaba.metrics.Meter;
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
@State(Scope.Benchmark)
public class MeterTest {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
          static Meter meter = MetricManager.getMeter("test",MetricName.build("com.test.meter"));
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(MeterTest.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .build();
        new Runner(op).run();
    }

    @TearDown(Level.Trial)
    public void check(){
        System.out.println(JSON.toJSONString(BenchmarkState.meter.getOneMinuteRate()));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public void meter_inc(BenchmarkState state){
        state.meter.mark();
        /**
         MeterTest.meter_inc  thrpt   10  44483312.150 ± 5716804.081  ops/s
         MeterTest.meter_inc   avgt   10        ≈ 10⁻⁷                 s/op
         */
    }
}
