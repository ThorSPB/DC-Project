package testbench;

import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;

public class Main {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger consoleLogger = new ConsoleLogger();
        ILogger fileLogger = new FileLogger("benchmark_log.txt");

        timer.start();
        // Example code to measure
        for (int i = 0; i < 1000000; i++);
        long elapsed = timer.stop();

        consoleLogger.write("Elapsed time (ns):", elapsed);
        fileLogger.write("Elapsed time (ns):", elapsed);

        consoleLogger.close();
        fileLogger.close();
    }
}
