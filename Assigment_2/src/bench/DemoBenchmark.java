package bench;

import java.util.ArrayList;
import java.util.Random;

public class DemoBenchmark implements IBenchmark{
    ArrayList<Integer> array;

    @Override
    public void run() {
        System.out.println("DemoBenchmark started...");
        int found = 1;
        while(found != 0){
            found = 0;
            for(int i = 0; i < array.size() - 1; i++){
                if(array.get(i) < array.get(i + 1)){
                    found = 1;
                    Integer aux = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, aux);
                }
            }
        }
        System.out.println("DemoBenchmark finished.");
    }

    @Override
    public void run(Object... params) {

    }

    @Override
    public void initialize(Object... params) {
        int size = (int) params[0];
        array = new ArrayList<>(size);
        System.out.println(array.size() + " " + size);
        Random random = new Random();
        for(int i = 0; i < size; i++)
            array.add(random.nextInt(1000));
    }

    @Override
    public void clean() {

    }

    @Override
    public void cancel() {

    }
}
