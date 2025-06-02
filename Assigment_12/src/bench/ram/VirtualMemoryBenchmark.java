package bench.ram;

import java.io.IOException;
import java.util.Random;
import java.text.DecimalFormat;
import timing.Timer;
import bench.IBenchmark;

/**
 * Maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower speeds).
 */

public class VirtualMemoryBenchmark implements IBenchmark {

    private String result = "";
    private MemoryMapper core = null;

    @Override
    public void initialize(Object... params) {
        /* not today */
    }

    @Override
    public void warmUp() {
        /* summer is coming anyway */
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object[]) instead");
    }

    @Override
    public void run(Object... options) {
        // expected: {fileSize, bufferSize}
        Object[] params = (Object[]) options;
        long fileSize = Long.parseLong(params[0].toString()); // e.g. 2-16GB
        int bufferSize = Integer.parseInt(params[1].toString()); // e.g. 4+KB

        try {
            core = new MemoryMapper("core", fileSize); // Change path as needed
            byte[] buffer = new byte[bufferSize];
            Random rand = new Random();

            Timer timer = new Timer();
            DecimalFormat df = new DecimalFormat("#.00");

            // write to VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                rand.nextBytes(buffer);
                core.put(i, buffer);
            }
            double time = timer.stop() / 1_000_000_000.0; // convert ns to seconds
            double speed = (fileSize / (1024.0 * 1024.0)) / time; // MB/s
            // 3. fileSize/time [MB/s]

            result = "\nWrote " + (fileSize / 1024 / 1024L)
                    + " MB to virtual memory at " + df.format(speed) + " MB/s";

            // read from VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                core.get(i, bufferSize); // 5. get from memory mapper
            }
            time = timer.stop() / 1_000_000_000.0; // <-- Add this division here
            speed = (fileSize / (1024.0 * 1024.0)) / time; // 6. MB/s

            result += "\nRead " + (fileSize / 1024 / 1024L)
                    + " MB from virtual memory at " + df.format(speed) + " MB/s";

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (core != null)
                core.purge();
        }
    }

    @Override
    public void cancel() {
        // not used
    }

    @Override
    public void clean() {
        if (core != null)
            core.purge();
    }

    @Override
    public void cleanUp() {
        // not used
    }

    @Override
    public String getResult() {
        return result;
    }

}
