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
    public void close() {
        // No action needed for console
    }
}
