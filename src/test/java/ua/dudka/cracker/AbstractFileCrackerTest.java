package ua.dudka.cracker;

import org.junit.After;
import ua.dudka.coder.FileEncoder;

abstract class AbstractFileCrackerTest {
    private static final String SPENT_TIME_MSG = "%s: %.2f sec\n";
    static final String MESSAGE = "Text that you can encode";
    String filename;


    @After
    public void tearDown() throws Exception {
        FileEncoder.deleteData(filename);
    }

    void showSpentTime(String methodName, long before, long after) {
        System.out.printf(SPENT_TIME_MSG, methodName, ((float) (after - before)) / 1000);
    }
}
