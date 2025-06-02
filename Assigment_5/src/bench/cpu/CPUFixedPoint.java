package bench.cpu;

import bench.IBenchmark;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILog;

public class CPUFixedPoint implements IBenchmark {
    private int size; // workload size
    private Timer timer;
    private ILog logger;

    private static final int OPERATIONS_PER_ITERATION = 45;

    @Override
    public void initialize(Object... params) {
        this.size = (int) params[0];
        this.timer = new Timer();
        this.logger = new ConsoleLogger();
    }

    @Override
    public void warmUp() {
        run(size / 10); // small warm-up
    }

    @Override
    public void run() {
        // not yet
    }

    @Override
    public void run(Object... options) {
        int ops = 0;

        timer.start();
        ops += integerArithmeticTest();
        ops += branchingTest();
        ops += arrayAccessTest();
        timer.stop();

        double timeInSeconds = timer.read() / 1e9; // timer gives nanoseconds
        double mops = (ops / timeInSeconds) / 1e6; // MOPS formula // the same as OPERATIONS_PER_ITERATION = 45

        logger.write("MOPS: " + mops);
    }


    private int integerArithmeticTest() {
        int operations = 0;
        int[] num = {1, 2, 3, 4};
        int j = 1, k = 2, l = 3;
        int[] res = new int[size];

        for (int i = 2; i < size; i++) {
            j = num[1] * (k - j) * (l - k);  // 5 ops
            k = num[3] * k - (l - j) * k;    // 5 ops
            l = (l - k) * (num[2] + j);      // 4 ops
            res[l % size] = j + k + l;       // 5 ops (modulo + assignment + 2 array access + 2 additions)
            res[k % size] = j * k * l;       // 4 ops (modulo + assignment + 2 array access + 2 multiplications)

            operations += 23; // TOTAL per iteration (counted carefully)
        }
        return operations;
    }

    private int branchingTest() {
        int operations = 0;
        int[] num = {0, 1, 2, 3};
        int j = 1;

        for (int i = 0; i < size; i++) {
            if (j == 1) {        // 1 comparison
                j = num[2];      // 1 assignment + 1 array access
            } else {
                j = num[3];      // 1 assignment + 1 array access
            }
            if (j > 2) {         // 1 comparison
                j = num[0];      // 1 assignment + 1 array access
            } else {
                j = num[1];      // 1 assignment + 1 array access
            }
            if (j < 1) {         // 1 comparison
                j = num[1];      // 1 assignment + 1 array access
            } else {
                j = num[0];      // 1 assignment + 1 array access
            }

            operations += 12; // TOTAL per iteration
        }
        return operations;
    }

    private int arrayAccessTest() {
        int operations = 0;
        int[] a = new int[size];
        int[] b = new int[size];
        int[] c = new int[size];

        // Initialize arrays
        for (int i = 0; i < size; i++) {
            a[i] = i;
            b[i] = size - i;
            operations += 2; // assignment and subtraction
        }

        for (int i = 0; i < size; i++) {
            c[i] = a[b[i] % size]; // modulo, 2 array access, assignment
            operations += 4;
        }
        return operations;
    }

    @Override
    public void cancel() {
        // Not needed now
    }

    @Override
    public void clean() {
        // not needed
    }

    @Override
    public void cleanUp() {
        // No cleanup needed
    }

    @Override
    public String getResult() {
        return "Fixed Point Benchmark Completed";
    }

}

