package ua.dudka.cracker;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import ua.dudka.coder.FileEncoder;
import ua.dudka.entity.Entity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BilateralFileCrackerTest extends AbstractFileCrackerTest {

    @Rule
    public TestName name = new TestName();
    private final FileCracker fileCracker = new BilateralFileCracker();

    @Test
    public void crackingFileWithPassLengthEq2() throws Exception {
        String password = "xp";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    private Entity encodeTestEntity(String password) {
        Entity testEntity = new Entity(MESSAGE, password);
        filename = FileEncoder.encode(testEntity);
        return testEntity;
    }


    @Test
    public void crackingFileWithPassLengthEq3() throws Exception {
        String password = "v0e";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    @Test
    public void crackingFileWithFirstAlphabeticCharsLengthEq3() throws Exception {
        String password = "abc";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    @Test
    public void crackingFileWithLastAlphabeticCharsLengthEq3() throws Exception {
        String password = "xyz";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    @Test
    public void crackingFileWithPassLengthEq4() throws Exception {
        String password = "9z1b";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    @Test
    public void crackingFileWithPassContainsOnlyNumbersLengthEq4() throws Exception {
        String password = "2016";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

    @Ignore("If you wanna check the performance of your computer - simply run the test\n")
    @Test
    public void crackingFileWithPassLengthEq5() throws Exception {
        String password = "1h0r9";
        Entity expectedEntity = encodeTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(name.getMethodName(), before, after);
    }

}