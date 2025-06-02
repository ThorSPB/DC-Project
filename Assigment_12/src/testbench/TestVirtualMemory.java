package testbench;

import bench.ram.VirtualMemoryBenchmark;

public class TestVirtualMemory {
    public static void main(String[] args) {
        VirtualMemoryBenchmark bench = new VirtualMemoryBenchmark();

        long fileSize = 10L * 1024 * 1024 * 1024; // 2 GB
        int bufferSize = 1024 * 1024; // 1 MB

        bench.run(new Object[]{fileSize, bufferSize});
        System.out.println("2GB test = " + bench.getResult());

        // Test with 8 GB
        //fileSize = 8L * 1024 * 1024 * 1024; // 8 GB
        //bench.run(new Object[]{fileSize, bufferSize});
        // System.out.println("8GB Test: " + bench.getResult());

        // Test with 16 GB ----> TEST ON EBI OR SEBI LAPTOP!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // fileSize = 16L * 1024 * 1024 * 1024; // 16 GB
        // bench.run(new Object[]{fileSize, bufferSize});
        // System.out.println("16GB Test: " + bench.getResult());

        //  TEST ALSO FOR 24 gb
    }
}
