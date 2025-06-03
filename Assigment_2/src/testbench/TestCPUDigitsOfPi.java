package testbench;

import bench.benchmark.cpu.CPUDigitsOfPi;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.Timer;
import timing.ITimer;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        IBenchmark benchmark = new CPUDigitsOfPi();
        ITimer timer = new Timer();
        ILogger consoleLogger = new ConsoleLogger();

        int digits = 50000;

        benchmark.initialize(digits);

        System.out.println("[Warmup phase]");

        for (int algorithm = 0; algorithm <= 2; algorithm += 2) {
            System.out.println("\n[Run with algorithm " + algorithm + "]");

            for (int i = 1; i <= 3; i++) {
                timer.start();
                benchmark.run(algorithm);
                long time = timer.stop();

                consoleLogger.writeTime("Finished " + i + " in ", time, TimeUnit.MILLISECONDS);
            }
        }

        benchmark.clean();
    }
}
