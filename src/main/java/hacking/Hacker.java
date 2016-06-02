package hacking;


import entity.Entity;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipInputStream;


public class Hacker {
    private byte[] bytes;
    private char[] allowableChars;
    private final ExecutorService passLenExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ExecutorService taskExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
    private final Entity entity = new Entity("", "", "");

    public Hacker(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes, 0, fileInputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Character> chars = new ArrayList<>();
        for (char i = 'a'; i <= 'z'; i++) {
            chars.add(i);
        }
        for (char i = '0'; i <= '9'; i++) {
            chars.add(i);
        }
        allowableChars = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            allowableChars[i] = chars.get(i);
        }

    }

    public Entity bruteForce() {
        for (int i = 1; i <= 5; i++) {
            final int length = i;
            passLenExecutor.submit(() -> {
                char[] pass = new char[length];
                iteratePass(pass, pass.length - 1, chars -> {
                    String message = decodeFile(String.valueOf(chars));
                    if (message.length() > 0) {
                        entity.setMessage(message);
                        entity.setPassword(String.valueOf(chars));
                        return;
                    }
                });
            });
        }

        while (entity.getMessage().length() < 1) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        passLenExecutor.shutdown();
        return entity;
    }

    public Entity doubleBruteForce(final int passLength) {

        taskExecutor.submit(() -> {
            char[] pass = new char[passLength];
            reverseIteratePass(pass, pass.length - 1, chars -> {
                String message = decodeFile(String.valueOf(chars));
                if (message.length() > 0) {
                    entity.setMessage(message);
                    entity.setPassword(String.valueOf(chars));
                    return;
                }
            });
        });

        taskExecutor.submit(() -> {
            char[] newpass = new char[passLength];
            iteratePass(newpass, newpass.length - 1, chars -> {
                String message = decodeFile(String.valueOf(chars));
                if (message.length() > 0) {
                    entity.setMessage(message);
                    entity.setPassword(String.valueOf(chars));
                    return;
                }
            });
        });

        while (entity.getMessage().length() < 1) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        taskExecutor.shutdown();
        return entity;
    }


    private void iteratePass(char[] password, int currentPos, Callback<char[]> callback) {
        if (currentPos > -1) {
            for (int i = 0; i < allowableChars.length; i++) {
                password[currentPos] = allowableChars[i];
                if (currentPos == 0) {
                    char[] buff = new char[password.length];
                    System.arraycopy(password, 0, buff, 0, password.length);
                    callback.call(password);
                } else {
                    iteratePass(password, currentPos - 1, callback);
                }
            }
        }
    }

    private void reverseIteratePass(char[] password, int currentPos, Callback<char[]> callback) {
        if (currentPos > -1) {
            for (int i = allowableChars.length - 1; i >= 0; i--) {
                password[currentPos] = allowableChars[i];
                if (currentPos == 0) {
                    char[] buff = new char[password.length];
                    System.arraycopy(password, 0, buff, 0, password.length);
                    callback.call(password);
                } else {
                    iteratePass(password, currentPos - 1, callback);
                }
            }
        }
    }

    private String decodeFile(String password) {
        try {
            Cipher encrypt = Cipher.getInstance("AES");
            encrypt.init(Cipher.DECRYPT_MODE, getKey(password));
            ZipInputStream zipInputStream = new ZipInputStream(new CipherInputStream(new ByteArrayInputStream(bytes), encrypt));
            BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
            zipInputStream.getNextEntry();

            StringBuilder builder = new StringBuilder();
            String srt;
            while ((srt = reader.readLine()) != null) {
                builder.append(srt);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Key getKey(String password) throws NoSuchAlgorithmException {
        byte[] pass = MessageDigest.getInstance("MD5").digest(password.getBytes());
        Key key = new SecretKeySpec(pass, "AES");
        return key;
    }

}

interface Callback<T> {
    void call(T t);
}
