package org.polytechtours.javaperformance.tp.paintingants.test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkAnts {
    private final PaintingAnts paint= new PaintingAnts();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkAnts.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getAppletInfo() {
        paint.getAppletInfo();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getParameterInfo() {
        paint.getParameterInfo();
    }
}
