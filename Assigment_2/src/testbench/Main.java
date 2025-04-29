package testbench;

import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;
import bench.DummyBenchmark;
import bench.IBenchmark;

public class Main {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger consoleLogger = new ConsoleLogger();
        ILogger fileLogger = new FileLogger("benchmark_log.txt");

        IBenchmark bench = new DummyBenchmark();

        // pass the size of array to work on with dummy operations
        bench.initialize(100000);

        timer.start();
        bench.run();

        // log the time after the benchmark run
        consoleLogger.write("Finished in", timer.stop(), "ns");

        fileLogger.write("Finished in", timer.stop(), "ns");


        bench.clean(); // free memory

        consoleLogger.close();
        fileLogger.close();
    }
}
