package bench.cpu;

public class CPURecursionLoopUnrolling {
    private long size;
    private int primeCount;
    private int callCount;
    private long nextNumber;
    private long currentStart;
    private boolean useUnrolling;
    private int unrollLevel;

    public CPURecursionLoopUnrolling(long size) {
        this.size = size;
    }

    public Result run(boolean useUnrolling, int unrollLevel) {
        resetState();
        this.useUnrolling = useUnrolling;
        this.unrollLevel = unrollLevel;

        long startTime = System.nanoTime();
        try {
            if (useUnrolling) {
                recursiveUnrolled(1, 0);
            } else {
                recursive(1, 0);
            }
            nextNumber = size + 1; // Completed normally
        } catch (StackOverflowError e) {
            nextNumber = currentStart; // Store crash point
        }
        long runtime = (System.nanoTime() - startTime) / 1_000_000; // ms

        return new Result(primeCount, nextNumber, callCount, runtime);
    }

    private void resetState() {
        primeCount = 0;
        callCount = 0;
        currentStart = 1;
        nextNumber = 1;
    }

    private long recursive(long start, int counter) {
        callCount++;
        currentStart = start;

        if (start > size) return 0;
        long sum = 0;

        if (isPrime(start)) {
            primeCount++;
            sum += start;
        }
        return sum + recursive(start + 1, counter + 1);
    }

    private long recursiveUnrolled(long start, int counter) {
        callCount++;
        currentStart = start;

        if (start > size) return 0;
        long sum = 0;
        long next = start;
        int processed = 0;

        while (processed < unrollLevel && next <= size) {
            if (isPrime(next)) {
                primeCount++;
                sum += next;
            }
            next++;
            processed++;
        }

        if (next <= size) {
            sum += recursiveUnrolled(next, counter + 1);
        }
        return sum;
    }

    private boolean isPrime(long x) {
        if (x <= 1) return false;
        if (x == 2) return true;
        if (x % 2 == 0) return false;

        long limit = (long) Math.sqrt(x);
        for (long i = 3; i <= limit; i += 2) {
            if (x % i == 0) return false;
        }
        return true;
    }

    public static class Result {
        public final int primeCount;
        public final long nextNumber;
        public final int callCount;
        public final long runtime;
        public final double score;

        public Result(int primeCount, long nextNumber, int callCount, long runtime) {
            this.primeCount = primeCount;
            this.nextNumber = nextNumber;
            this.callCount = callCount;
            this.runtime = runtime;
            this.score = (runtime > 0) ? (double) primeCount / runtime : 0;
        }
    }
}