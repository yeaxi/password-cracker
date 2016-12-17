package ua.dudka.coder;

import org.junit.After;
import org.junit.Test;
import ua.dudka.entity.Entity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class FileEncoderTest {
    private static final String MESSAGE = "Test message";

    private String filename = "";

    @After
    public void tearDown() throws Exception {
        FileEncoder.deleteData(filename);

    }

    @Test
    public void encodeEntityShouldCreateEncodedFile() throws Exception {
        String password = "asd1s";
        Entity expected = prepareTestEntity(password);

        this.filename = FileEncoder.encode(expected);

        assertTrue(Files.exists(Paths.get(filename)));
    }

    private Entity prepareTestEntity(String password) {
        return new Entity(MESSAGE, password);
    }

    private Entity prepareAndEncodeEntity(String password) {
        Entity entity = prepareTestEntity(password);
        this.filename = FileEncoder.encode(entity);
        return entity;
    }

    @Test
    public void decodeFileByFilenameAndPasswordShouldReturnMessage() throws Exception {
        String password = "aoiug_h90q834]'";
        Entity entity = prepareAndEncodeEntity(password);

        String decodedMessage = FileEncoder.decode(filename, password);

        assertEquals(entity.getMessage(), decodedMessage);
    }

    @Test(expected = FileNotFoundRuntimeException.class)
    public void decodeNonexistentFileShouldThrowFileNotFoundRuntimeException() throws Exception {
        String password = "aoiug_h90q834]'";
        prepareAndEncodeEntity(password);

        FileEncoder.decode(filename + "asdasd", password);
    }

    @Test
    public void decodeFileWithWrongPassShouldReturnEmptyString() throws Exception {
        String password = "azxf57rqws";
        prepareAndEncodeEntity(password);

        String decodedMessage = FileEncoder.decode(filename, password + "123");

        assertTrue(decodedMessage.isEmpty());
    }

    @Test
    public void deleteDataShouldDeleteExistentFile() throws Exception {
        String pass = "asfadsf";
        prepareAndEncodeEntity(pass);
        Path path = Paths.get(filename);

        assertTrue(Files.exists(path));

        FileEncoder.deleteData(filename);

        assertFalse(Files.exists(path));

    }

    @Test
    public void deleteNonexistentFileShouldDoNothing() throws Exception {
        filename = "asdasdasdq14e2";
        Path path = Paths.get(filename);

        assertFalse(Files.exists(path));

        FileEncoder.deleteData(filename);

        assertFalse(Files.exists(path));
    }
}