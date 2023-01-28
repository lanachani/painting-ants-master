package org.polytechtours.javaperformance.tp.paintingants.test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.polytechtours.javaperformance.tp.paintingants.CFourmi;
import org.polytechtours.javaperformance.tp.paintingants.CPainting;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;
import org.polytechtours.javaperformance.tp.paintingants.Parameters;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkAnts {
    private final PaintingAnts paint = new PaintingAnts();
    private final Parameters param = new Parameters();
    private final CPainting painting = new CPainting(new Dimension(100, 100), paint);
    private final CFourmi fourmi = new CFourmi(new Color(255, 0, 0), 0.3f, 0.3f, 0.4f,
            0.5f, painting, 'd', 1, 1, 50, paint);

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
        param.getParameterInfo();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getDeplacements() {
        fourmi.getNbDeplacements();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getX() { fourmi.getX(); }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void getY() { fourmi.getY(); }
}
