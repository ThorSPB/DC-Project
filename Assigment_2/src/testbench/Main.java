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
        // 1st block for timing
        for (int i = 0; i < 500000; i++);   // dummy work
        long partial_time = timer.pause();
        consoleLogger.write("Timer before pause = ", partial_time + " ns");

        timer.resume();
        // 2nd block
        for (int i = 0; i < 500000; i++);
        long total = timer.stop();                          // both parts: before & after pause
        consoleLogger.write("Total time = ", total + " ns");

        fileLogger.write("Total time = ", total + " ns");

        consoleLogger.close();
        fileLogger.close();
    }
}
