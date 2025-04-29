package timing;

// interface for timer functionality

/**
 * interface for timer functionality
 */

public interface ITimer {
    void start();
    long stop();
    void resume();
    long pause();
}
