package coder;

import entity.Entity;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by RASTA on 28.05.2016.
 */
public class FileEncriptorTest {

    @Test
    public void first() throws Exception {
        String file = "test1";
        String message = "testMessage";
        String password = "passwordBeach";

        Entity entity = new Entity(file, message, password);
        FileEncriptor.encryptEntity(entity);
        assertEquals(message, FileEncriptor.decode(file, password));
    }

    @Test
    public void second() throws Exception {
        String file = "test2";
        String message = "12345678901234567890123456789012345678901234567890";
        String password = "lskd;jfwopijefcn;osdljaghf2-";

        Entity entity = new Entity(file, message, password);
        FileEncriptor.encryptEntity(entity);

        assertEquals(message, FileEncriptor.decode(file, password));
    }
}