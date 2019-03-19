package com.lzl.metric;

import com.alibaba.metrics.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author lizanle
 * @Date 2019/3/19 11:13
 */
@State(Scope.Benchmark)
public class CompassTest {
    private Compass compass = new CompassImpl(1, ReservoirType.BUCKET);
    private FastCompass fastCompass = new FastCompassImpl(1);

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public Object testCompassPerformance() {
        compass.update(1, TimeUnit.MILLISECONDS);
        return compass;
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public Object testFastCompassPerformance() {
        fastCompass.record(1, "success");
        return fastCompass;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + CompassTest.class.getSimpleName() + ".*")
                .warmupIterations(3)
                .measurementIterations(5)
                .threads(32)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
    /**
     CompassTest.testCompassPerformance      thrpt    5  24501015.715 ± 5184203.651  ops/s
     CompassTest.testFastCompassPerformance  thrpt    5  71620828.810 ± 3322514.032  ops/s
     CompassTest.testCompassPerformance       avgt    5        ≈ 10⁻⁶                 s/op
     CompassTest.testFastCompassPerformance   avgt    5        ≈ 10⁻⁶                 s/op
     */
}
