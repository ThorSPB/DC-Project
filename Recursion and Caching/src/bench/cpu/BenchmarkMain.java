package bench.cpu;

public class BenchmarkMain {
    private static final long SIZE = 2_000_000;
    private static final int[] UNROLL_LEVELS = {0, 1, 5, 15};
    private static final int RUNS_PER_LEVEL = 5;

    public static void main(String[] args) {
        if (args.length == 0) {
            runCompositeBenchmark();
        } else {
            parseArguments(args);
        }
    }

    private static void runCompositeBenchmark() {
        CPURecursionLoopUnrolling benchmark = new CPURecursionLoopUnrolling(SIZE);
        double totalScore = 0;

        for (int level : UNROLL_LEVELS) {
            double levelScore = 0;
            for (int i = 0; i < RUNS_PER_LEVEL; i++) {
                boolean useUnroll = (level != 0);
                CPURecursionLoopUnrolling.Result result = benchmark.run(useUnroll, level);
                levelScore += result.score;
            }
            totalScore += levelScore / RUNS_PER_LEVEL;
        }

        System.out.println(totalScore / UNROLL_LEVELS.length);
    }

    private static void parseArguments(String[] args) {
        try {
            if (args.length == 1 && "false".equalsIgnoreCase(args[0])) {
                runSingle(false, 0);
            } else if (args.length == 2 && "true".equalsIgnoreCase(args[0])) {
                runSingle(true, Integer.parseInt(args[1]));
            } else {
                printUsage();
            }
        } catch (NumberFormatException e) {
            printUsage();
        }
    }

    private static void runSingle(boolean useUnrolling, int unrollLevel) {
        CPURecursionLoopUnrolling benchmark = new CPURecursionLoopUnrolling(SIZE);
        CPURecursionLoopUnrolling.Result result = benchmark.run(useUnrolling, unrollLevel);

        System.out.println("Reached nr " + result.nextNumber + "/" + SIZE +
                (useUnrolling ? " at " + unrollLevel + " levels" : "") +
                " after " + result.callCount + " calls");
        System.out.println("Primes found: " + result.primeCount);
        System.out.println("Runtime: " + result.runtime + " ms");
        System.out.println("Score: " + result.score);
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Composite benchmark: java -jar benchmark.jar");
        System.out.println("  Single run (no unroll): java -jar benchmark.jar false");
        System.out.println("  Single run (with unroll): java -jar benchmark.jar true <LEVEL>");
    }
}