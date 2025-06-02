package testbench;

import bench.ram.VirtualMemoryBenchmark;

public class TestVirtualMemory {
    public static void main(String[] args) {
        VirtualMemoryBenchmark bench = new VirtualMemoryBenchmark();

        long fileSize = 2L * 1024 * 1024 * 1024; // 2 GB
        int bufferSize = 1024 * 1024; // 1 MB

        bench.run(new Object[]{fileSize, bufferSize});
        System.out.println(bench.getResult());
    }
}
