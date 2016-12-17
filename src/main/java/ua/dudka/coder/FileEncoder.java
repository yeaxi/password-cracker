package ua.dudka.coder;

import ua.dudka.entity.Entity;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileEncoder {

    private static final String AES_CIPHER = "AES";
    private static final String DIRECTORY_NAME = "data/";

    static {
        if (Files.notExists(Paths.get(DIRECTORY_NAME))) {
            try {
                Files.createDirectory(Paths.get(DIRECTORY_NAME));
            } catch (IOException ignored) {
            }
        }
    }

    public static String encode(Entity entity) {
        try {
            String filename = generateFilename();
            ZipOutputStream zipOutputStream = getCipherZipOutputStream(filename, entity.getPassword());

            ZipEntry zipEntry = new ZipEntry("data");
            PrintWriter writer = new PrintWriter(zipOutputStream);
            zipOutputStream.putNextEntry(zipEntry);
            writer.println(entity.getMessage());

            writer.flush();
            writer.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String generateFilename() {
        String filename = DIRECTORY_NAME + UUID.randomUUID() + "_" + UUID.randomUUID();
        return filename.substring(0, new Random().nextInt(filename.length()));
    }


    private static ZipOutputStream getCipherZipOutputStream(String filename, String password) throws Exception {
        Cipher encrypt = Cipher.getInstance(AES_CIPHER);
        encrypt.init(Cipher.ENCRYPT_MODE, getKey(password));
        return new ZipOutputStream(new CipherOutputStream(new FileOutputStream(filename), encrypt));
    }


    public static String decode(String filename, String password) throws FileNotFoundRuntimeException {
        try {
            BufferedReader reader = createBufferedReader(filename, password);

            String srt;
            StringBuilder builder = new StringBuilder();
            while ((srt = reader.readLine()) != null) {
                builder.append(srt);
            }
            reader.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundRuntimeException();
        } catch (Exception e) {
            return "";
        }

    }

    private static BufferedReader createBufferedReader(String filename, String password) throws Exception {
        ZipInputStream zipInputStream = getCipherZipInputStream(filename, password);
        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        zipInputStream.getNextEntry();
        return reader;
    }

    private static ZipInputStream getCipherZipInputStream(String filename, String password) throws Exception {
        Cipher encrypt = Cipher.getInstance(AES_CIPHER);
        encrypt.init(Cipher.DECRYPT_MODE, getKey(password));
        return new ZipInputStream(new CipherInputStream(new FileInputStream(filename), encrypt));
    }

    public static void deleteData(String filename) {
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static Key getKey(String password) throws NoSuchAlgorithmException {
        byte[] pass = MessageDigest.getInstance("MD5").digest(password.getBytes());
        return new SecretKeySpec(pass, AES_CIPHER);
    }
}