package bench;

import java.util.Arrays;
import java.util.Random;

public class DummyBenchmark implements IBenchmark {
    private int[] array;

    @Override
    public void initialize(Object... params) {
        int size = (int) params[0]; // First parameter is the array size

        array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt();
        }
    }

    @Override
    public void run() {
        System.out.println("Benchmark started");
        // some tasks to test the time -> sorting
        Arrays.sort(array);
    }

    @Override
    public void run(Object... params) {
        // not yet implemented
        // maybe for complex tasks
        /*
        int additionalWork = (int) params[0]; maybe a treshhold or smth
        for (int i = 0; i < additionalWork; i++) {
            just dummy test
            array[i % array.length] = array[i % array.length] * 2;
        }
         */
    }

    @Override
    public void clean() {
        array = null;  // free memory
    }

    @Override
    public void cancel() {
    }
}
