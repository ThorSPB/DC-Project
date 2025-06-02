package testbench;

import bench.benchmark.cpu.CPUDigitsOfPi;
import bench.IBenchmark;
import timing.Timer;
import timing.ITimer;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        IBenchmark benchmark = new CPUDigitsOfPi();
        ITimer timer = new Timer();

        int digits = 500;

        benchmark.initialize(digits);

        System.out.println("[Warmup phase]");

        for (int algorithm = 1; algorithm <= 3; algorithm++) {
            System.out.println("\n[Run with algorithm " + algorithm + "]");

            for (int i = 1; i <= 3; i++) {
                timer.start();
                benchmark.run(algorithm);
                long time = timer.stop();
                System.out.println("Run " + i + " time: " + time + " ms");
            }
        }

        benchmark.clean();
    }
}
