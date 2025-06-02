package bench.benchmark.cpu;

import bench.IBenchmark;

import java.math.BigDecimal;
import java.math.MathContext;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;
    private static final MathContext MATH_CONTEXT = new MathContext(1000);

    @Override
    public void initialize(Object... params) {
        this.digits = (int) params[0];
    }

    @Override
    public void run() {
        run(1); // default to algorithm 1
    }

    @Override
    public void run(Object... options) {
        int algorithm = (int) options[0];
        switch (algorithm) {
            case 0:
                computePiMagically(digits);
                break;
            case 1:
                computePiByGuessing(digits);
                break;
            case 2:
                computePiUsingMaths(digits);
                break;
            default:
                throw new IllegalArgumentException("The number must be between 0 and 2");
        }
    }

    @Override
    public void clean() {
        // No resources to clean up for now
    }

    @Override
    public void cancel() {

    }

    private BigDecimal computePiMagically(int iterations) {
        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal one = BigDecimal.ONE;

        for (int k = 0; k < iterations; k++) {
            BigDecimal term = one.divide(BigDecimal.valueOf(2 * k + 1), MATH_CONTEXT);
            if (k % 2 == 0)
                pi = pi.add(term);
            else
                pi = pi.subtract(term);
        }

        pi = pi.multiply(BigDecimal.valueOf(4));
        return pi;
    }

    private BigDecimal computePiByGuessing(int terms) {
        BigDecimal pi = BigDecimal.valueOf(3);
        boolean add = true;

        for (int i = 2, t = 0; t < terms; i += 2, t++) {
            BigDecimal term = BigDecimal.valueOf(4).divide(
                    BigDecimal.valueOf(i * (i + 1) * (i + 2)), MATH_CONTEXT);
            pi = add ? pi.add(term) : pi.subtract(term);
            add = !add;
        }

        return pi;
    }

    private BigDecimal computePiUsingMaths(int precision) {
        BigDecimal arctan1_5 = arctan(5, precision);
        BigDecimal arctan1_239 = arctan(239, precision);

        BigDecimal pi = arctan1_5.multiply(BigDecimal.valueOf(16))
                .subtract(arctan1_239.multiply(BigDecimal.valueOf(4)));

        return pi.setScale(precision, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal arctan(int inverseX, int precision) {
        BigDecimal result, term;
        BigDecimal x = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX), MATH_CONTEXT);
        BigDecimal xPower = x;

        result = x;
        for (int i = 1; i < precision; i++) {
            int k = 2 * i + 1;
            xPower = xPower.multiply(x).multiply(x).negate();
            term = xPower.divide(BigDecimal.valueOf(k), MATH_CONTEXT);
            result = result.add(term);
        }
        return result;
    }
}
