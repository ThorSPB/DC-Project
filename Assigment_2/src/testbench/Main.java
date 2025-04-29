package testbench;

import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import logging.TimeUnit;
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

        // Initialize benchmark with workload
        bench.initialize(100000);

        timer.start();
        bench.run();

        // Log total time
        long totalTime = timer.stop();
        consoleLogger.writeTime("Finished in", totalTime, TimeUnit.MILLISECONDS);
        fileLogger.writeTime("Finished in", totalTime, TimeUnit.MILLISECONDS);

        // Cleanup
        bench.clean();
        consoleLogger.close();
        fileLogger.close();
    }
}