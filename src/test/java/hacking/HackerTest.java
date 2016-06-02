package hacking;

import coder.FileEncriptor;
import entity.Entity;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;


public class HackerTest {

    @Test
    public void length_3() throws Exception {
        String file = "test3";
        String message = "test#3 pass length 3";
        String password = "9z9";

        Entity entity = new Entity(file, message, password);
        FileEncriptor.encryptEntity(entity);


        long before = new Date().getTime();

        Hacker hacker = new Hacker("test3");

        System.out.println(hacker.bruteForce());

        long after = new Date().getTime();
        System.out.println(after - before);

        Files.delete(Paths.get(file));

    }

    @Test
    public void length_4() throws Exception {
        String file = "test4";
        String message = "test#4 pass length 4";
        String password = "x7f8";

        Entity entity = new Entity(file, message, password);
        FileEncriptor.encryptEntity(entity);


        long before = new Date().getTime();

        Hacker hacker = new Hacker("test4");
        System.out.println(hacker.bruteForce());

        long after = new Date().getTime();
        System.out.println(after - before);

        Files.delete(Paths.get(file));

    }

    @Test
    public void doubleBruteForce_length_4() throws Exception {
        String file = "double_test4";
        String message = "double_test#4 pass length 4";
        String password = "x7f8";

        Entity entity = new Entity(file, message, password);
        FileEncriptor.encryptEntity(entity);


        long before = new Date().getTime();

        Hacker hacker = new Hacker("double_test4");
        System.out.println(hacker.doubleBruteForce(4));

        long after = new Date().getTime();
        System.out.println(after - before);

        Files.delete(Paths.get(file));

    }

}