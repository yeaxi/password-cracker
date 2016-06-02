package coder;

import entity.Entity;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class FileEncriptor {

    public static void encryptEntity(Entity entity) {
        try {
            Cipher encrypt = Cipher.getInstance("AES");
            encrypt.init(Cipher.ENCRYPT_MODE, getKey(entity.getPassword()));

            ZipOutputStream zipOutputStream = new ZipOutputStream(new CipherOutputStream(new FileOutputStream(entity.getFileName()), encrypt));
            ZipEntry zipEntry = new ZipEntry("data");

            PrintWriter writer = new PrintWriter(zipOutputStream);
            zipOutputStream.putNextEntry(zipEntry);
            writer.println(entity.getMessage());

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decode(String file, String password) {
        try {
            Cipher encrypt = Cipher.getInstance("AES");
            encrypt.init(Cipher.DECRYPT_MODE, getKey(password));
            ZipInputStream zipInputStream = new ZipInputStream(new CipherInputStream(new FileInputStream(file), encrypt));
            BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
            zipInputStream.getNextEntry();
            String srt;
            StringBuilder builder = new StringBuilder();
            while ((srt = reader.readLine()) != null) {
                builder.append(srt);
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static Key getKey(String password) throws NoSuchAlgorithmException {

        byte[] pass = MessageDigest.getInstance("MD5").digest(password.getBytes());
        Key key = new SecretKeySpec(pass, "AES");
        System.out.println(Arrays.toString(pass));
        return key;
    }
}