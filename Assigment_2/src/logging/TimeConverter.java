package logging;

public class TimeConverter {
    public static double convert(long nanoseconds, TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return nanoseconds;
            case MICROSECONDS:
                return nanoseconds / 1e3;
            case MILLISECONDS:
                return nanoseconds / 1e6;
            case SECONDS:
                return nanoseconds / 1e9;
            default:
                throw new IllegalArgumentException("Invalid time unit");
        }
    }
}