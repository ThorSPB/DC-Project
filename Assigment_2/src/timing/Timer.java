package timing;

// Timer implementation using System.nanoTime().

public class Timer implements ITimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    /**
     * it starts the timer by recording current time
     */
    @Override
    public void start() {
        startTime = System.nanoTime();
        elapsedTime = 0;
        running = true;
    }

    @Override
    public long stop() {
        if (running) {
            elapsedTime += System.nanoTime() - startTime;
            running = false;
        }
        return elapsedTime;
    }

    @Override
    public void resume() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }

    @Override
    public long pause() {
        if (running) {
            elapsedTime += System.nanoTime() - startTime;
            running = false;
        }
        return elapsedTime;
    }
}
