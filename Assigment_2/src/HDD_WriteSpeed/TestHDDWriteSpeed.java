
package HDD_WriteSpeed;

public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        HDDWriteSpeed bench = new HDDWriteSpeed();

        // Run fixed file size benchmark (clean files after test)
        bench.run("fs", true);

        // Run fixed buffer size benchmark (do not clean files after test)
        bench.run("fb", false);
    }
}
