package bench;

public class CPUBenchmark implements IBenchmark {
    private int limit;

    @Override
    public void initialize(Object... params) {
        this.limit = (Integer) params[0];
    }

    @Override
    public void run() {
        calculatePrimes(limit);
    }

    @Override
    public void run(Object... params) {
        // Not used here
    }

    @Override
    public void clean() {
        // No cleanup needed
    }

    @Override
    public void cancel() {
        // Cancellation not implemented for simplicity
    }

    private long calculatePrimes(int limit) {
        long sum = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}