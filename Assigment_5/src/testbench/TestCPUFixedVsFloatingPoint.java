package testbench;

import bench.cpu.CPUFixedVsFloatingPoint;
import bench.cpu.NumberRepresentation;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILog;

public class TestCPUFixedVsFloatingPoint {
	public static void main(String[] args) {
		CPUFixedVsFloatingPoint benchmark = new CPUFixedVsFloatingPoint();
		ILog logger = new ConsoleLogger();
		Timer timer = new Timer();
		int workloadSize = 100_000_000; // large size for better measurement

		benchmark.initialize(workloadSize);
		benchmark.warmUp();

		timer.start();
		benchmark.run(NumberRepresentation.FIXED);
		timer.stop();
		double fixedTime = timer.read() / 1e9;
		logger.write("Fixed point time: " + fixedTime + " seconds");

		timer.start();
		benchmark.run(NumberRepresentation.FLOATING);
		timer.stop();
		double floatingTime = timer.read() / 1e9;
		logger.write("Floating point time: " + floatingTime + " seconds");
	}
}
