package HDD_RandomAccess;

public class Main {
    public static void main(String[] args) {
        HDDRandomAccess benchmark = new HDDRandomAccess();

        // Initialize with a 100MB test file
        long fileSize = 100L * 1024 * 1024;
        benchmark.initialize(fileSize);

        // Warm up phase (optional)
        benchmark.warmUp();

        // Run fixed-size read benchmark
        benchmark.run("r", "fs", 4 * 1024);
        System.out.println("READ FS: " + benchmark.getResult());

        // Run fixed-time read benchmark
        benchmark.run("r", "ft", 4 * 1024);
        System.out.println("READ FT: " + benchmark.getResult());

        // Run fixed-size write benchmark
        benchmark.run("w", "fs", 4 * 1024);
        System.out.println("WRITE FS: " + benchmark.getResult());

        // Run fixed-time write benchmark
        benchmark.run("w", "ft", 4 * 1024);
        System.out.println("WRITE FT: " + benchmark.getResult());

        benchmark.clean(); // optional cleanup
    }
}
