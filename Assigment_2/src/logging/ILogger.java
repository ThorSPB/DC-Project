package logging;

// interface for logging messages

/**
 * interface for logging messages
 */

public interface ILogger {
    void writeTime(String text, long value, TimeUnit unit);
    void writeTime(long value, TimeUnit unit);
    void write(long value);
    void write(String value);
    void write(Object... values);
    void close();
}
