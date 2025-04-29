package bench;

// interface defining a generic benchmark

public interface IBenchmark {
    void run();
    void run(Object... params);
    void initialize(Object... params);
    void clean();
    void cancel();
}
