package bench.benchmark.cpu;

import bench.IBenchmark;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;
    private MathContext mathContext;

    @Override
    public void initialize(Object... params) {
        this.digits = (int) params[0];
        this.mathContext = new MathContext(digits + 10, RoundingMode.HALF_UP);
    }

    @Override
    public void run() {
        run(0); // Default to Chudnovsky
    }

    @Override
    public void run(Object... options) {
        int algorithm = (int) options[0];
        BigDecimal pi;
        switch (algorithm) {
            case 0 -> pi = computePiChudnovsky(digits);
            case 1 -> pi = computePiMachin(digits);
            case 2 -> pi = computePiBBP(digits);
            default -> throw new IllegalArgumentException("Algorithm must be 0, 1, or 2");
        }
        String piStr = pi.toPlainString().replace(".", "");
    }

    @Override
    public void clean() {}

    @Override
    public void cancel() {}

    // Chudnovsky Algorithm
    private BigDecimal computePiChudnovsky(int digits) {
        int terms = (int) (digits / 14.1816474627254776555) + 1;
        BinarySplitResult result = binarySplit(0, terms);
        BigDecimal sqrtC = sqrt(new BigDecimal("10005"), mathContext);
        BigDecimal numerator = new BigDecimal(result.Q).multiply(new BigDecimal(426880)).multiply(sqrtC);
        BigDecimal pi = numerator.divide(new BigDecimal(result.T), mathContext);
        return pi;
    }

    private static class BinarySplitResult {
        BigInteger P, Q, T;
        BinarySplitResult(BigInteger P, BigInteger Q, BigInteger T) {
            this.P = P; this.Q = Q; this.T = T;
        }
    }

    private BinarySplitResult binarySplit(int a, int b) {
        if (b - a == 1) {
            long k = a;
            BigInteger P, Q, T;

            BigInteger sixKFact = factorial(6 * k);
            BigInteger threeKFact = factorial(3 * k);
            BigInteger kFact = factorial(k);
            Q = BigInteger.ONE;
            if (k != 0) {
                Q = BigInteger.valueOf(640320).pow(3 * (int)k).divide(BigInteger.valueOf(24));
            }

            P = sixKFact.multiply(BigInteger.valueOf(13591409 + 545140134L * k));
            P = P.divide(threeKFact.multiply(kFact.pow(3)));
            if (k % 2 != 0) P = P.negate();
            T = P;
            return new BinarySplitResult(P, Q, T);
        }

        int m = (a + b) / 2;
        BinarySplitResult left = binarySplit(a, m);
        BinarySplitResult right = binarySplit(m, b);
        BigInteger P = left.P.multiply(right.P);
        BigInteger Q = left.Q.multiply(right.Q);
        BigInteger T = right.Q.multiply(left.T).add(left.P.multiply(right.T));
        return new BinarySplitResult(P, Q, T);
    }

    private BigDecimal sqrt(BigDecimal A, MathContext mc) {
        BigDecimal x = new BigDecimal(Math.sqrt(A.doubleValue()), mc);
        BigDecimal two = BigDecimal.valueOf(2);
        for (int i = 0; i < 10; i++) {
            x = x.add(A.divide(x, mc)).divide(two, mc);
        }
        return x;
    }

    private BigInteger factorial(long n) {
        BigInteger result = BigInteger.ONE;
        for (long i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // Machin-like Formula
    private BigDecimal computePiMachin(int precision) {
        BigDecimal arctan1_5 = arctan(5, precision + 10);
        BigDecimal arctan1_239 = arctan(239, precision + 10);
        BigDecimal pi = arctan1_5.multiply(BigDecimal.valueOf(16))
                .subtract(arctan1_239.multiply(BigDecimal.valueOf(4)));
        return pi.round(mathContext);
    }

    private BigDecimal arctan(int inverseX, int terms) {
        BigDecimal result, term;
        BigDecimal x = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX), mathContext);
        BigDecimal xPower = x;
        result = x;
        for (int i = 1; i < terms; i++) {
            int k = 2 * i + 1;
            xPower = xPower.multiply(x).multiply(x).negate();
            term = xPower.divide(BigDecimal.valueOf(k), mathContext);
            result = result.add(term);
        }
        return result;
    }

    // BBP Formula (only supports hexadecimal digit extraction, slow in decimal)
    private BigDecimal computePiBBP(int digits) {
        BigDecimal pi = BigDecimal.ZERO;
        int terms = (int) (digits * 1.1); // conservative approximation
        for (int k = 0; k < terms; k++) {
            BigDecimal term = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(16).pow(k), mathContext)
                    .multiply(
                            BigDecimal.valueOf(4).divide(BigDecimal.valueOf(8 * k + 1), mathContext)
                                    .subtract(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(8 * k + 4), mathContext))
                                    .subtract(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 5), mathContext))
                                    .subtract(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 6), mathContext))
                    );
            pi = pi.add(term);
        }
        return pi.round(mathContext);
    }
}
