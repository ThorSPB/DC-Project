package testbench;

import bench.DemoBenchmark;
import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;
import bench.DummyBenchmark;
import bench.IBenchmark;

public class TestDemoBench {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger consoleLogger = new ConsoleLogger();
        ILogger fileLogger = new FileLogger("benchmark_log.txt");

        IBenchmark bench = new DummyBenchmark();

        // pass the size of array to work on with dummy operations
        bench.initialize(100000);

        timer.start();
        bench.run();
        long time = timer.stop();

        // log the time after the benchmark run
        consoleLogger.write("Finished in", time, "ns");

        fileLogger.write("Finished in", time, "ns");


        bench.clean(); // free memory

        IBenchmark demobench = new DemoBenchmark();

        // pass the size of array to work on with demo operations
        demobench.initialize(10000);

        timer.start();
        demobench.run();
        long time_demo = timer.stop();

        // log the time after the benchmark run
        consoleLogger.write("Finished in", time_demo / 1000, "ms");

        fileLogger.write("Finished in", time_demo / 1000, "ms");


        demobench.clean(); // free memory

        consoleLogger.close();
        fileLogger.close();
    }
}
