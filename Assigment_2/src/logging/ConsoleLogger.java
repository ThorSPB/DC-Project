package logging;

// logger implementation that writes to the console

public class ConsoleLogger implements ILogger {


    /**
     * write() --> write a long value to the output
     */

    @Override
    public void write(long value) {
        System.out.println(value);
    }

    @Override
    public void write(String value) {
        System.out.println(value);
    }

    @Override
    public void write(Object... values) {   //  will print all values separated by space
        for (Object obj : values) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    @Override
    public void writeTime(String text, long value, TimeUnit unit) {
        double converted = TimeConverter.convert(value, unit);
        System.out.println(text + " " + converted + " " + unit.name().toLowerCase());
    }

    @Override
    public void writeTime(long value, TimeUnit unit) {
        double converted = TimeConverter.convert(value, unit);
        System.out.println(converted + " " + unit.name().toLowerCase());
    }

    @Override
    public void close() {
        // No action needed for console
    }
}
