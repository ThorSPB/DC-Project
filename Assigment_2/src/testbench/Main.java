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

        long partial_time = timer.pause();
        consoleLogger.write("Timer before pause = ", partial_time + " ns");

        timer.resume();
        bench.run();
        long total_time = timer.stop();
        // log the time after the benchmark run
        consoleLogger.write("Finished in", total_time, "ns");

        fileLogger.write("Finished in", total_time, "ns");


        bench.clean(); // free memory

        consoleLogger.close();
        fileLogger.close();
    }
}
