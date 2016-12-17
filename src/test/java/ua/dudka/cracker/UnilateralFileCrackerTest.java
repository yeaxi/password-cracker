package ua.dudka.cracker;

import org.junit.Ignore;
import org.junit.Test;
import ua.dudka.coder.FileEncoder;
import ua.dudka.entity.Entity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UnilateralFileCrackerTest extends AbstractFileCrackerTest {

    private final FileCracker fileCracker = new UnilateralFileCracker();


    @Test
    public void crackingFileWithPassLengthEq2() throws Exception {
        String password = "ff";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }


    private Entity prepareTestEntity(String password) {
        Entity testEntity = new Entity(MESSAGE, password);
        filename = FileEncoder.encode(testEntity);
        return testEntity;
    }

    @Test
    public void crackingFileWithPassLengthEq3() throws Exception {
        String password = "z0x";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }

    @Test
    public void crackingFileWithFirstAlphabeticCharsLengthEq3() throws Exception {
        String password = "cda";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }

    @Test
    public void crackingFileWithPassLengthEq4() throws Exception {
        String password = "9fpa";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }

    @Test
    public void crackingFileWithPassContainsOnlyNumbersLengthEq4() throws Exception {
        String password = "1998";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }

    @Ignore("If you want to check the performance of your computer - simply run the test")
    @Test
    public void crackingFileWithPassLengthEq5() throws Exception {
        String password = "zfx96";
        Entity expectedEntity = prepareTestEntity(password);

        long before = new Date().getTime();
        Entity crackedEntity = fileCracker.crack(filename);
        long after = new Date().getTime();

        assertEquals(expectedEntity, crackedEntity);
        showSpentTime(before, after);
    }

}