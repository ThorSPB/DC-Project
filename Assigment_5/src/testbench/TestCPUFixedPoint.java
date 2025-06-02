package testbench;

import bench.cpu.CPUFixedPoint;

public class TestCPUFixedPoint {
    public static void main(String[] args) {
        CPUFixedPoint benchmark = new CPUFixedPoint();
        int workloadSize = 10_000_000; // example workload

        benchmark.initialize(workloadSize);
        benchmark.warmUp();
        benchmark.run(workloadSize);
        benchmark.cleanUp();
    }
}
