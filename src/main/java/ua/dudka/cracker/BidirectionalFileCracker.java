package ua.dudka.cracker;

import ua.dudka.coder.FileEncoder;
import ua.dudka.entity.Entity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BidirectionalFileCracker implements FileCracker {
    private char[] allowableChars;
    private ExecutorService executorService;
    private Entity entity;
    private String filename;

    public BidirectionalFileCracker() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.entity = new Entity("", "");
        allowableChars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    }

    @Override
    public Entity crack(String filename) {
        setFilename(filename);
        startCracking();
        waitForAnswerAndShutdown();
        return entity;
    }

    private void setFilename(String fileName) {
        this.filename = fileName;
    }

    private void waitForAnswerAndShutdown() {
        while (entity.getMessage().length() < 1) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdownNow();
    }

    private void startCracking() {
        for (int passLen = 1; passLen <= 5; passLen++) {
            bilateralCrack(passLen);
        }
    }

    private void bilateralCrack(final int passLength) {
        executorService.submit(() -> {
            char[] passChars = new char[passLength];
            iteratePass(passChars, passChars.length - 1, callback);
        });

        if (passLength > 1) {
            executorService.submit(() -> {
                char[] passChars = new char[passLength];
                reverseIteratePass(passChars, passChars.length - 1, callback);
            });
        }

    }

    private Callback<char[]> callback = chars -> {
        String password = String.valueOf(chars);
        String message = FileEncoder.decode(filename, password);
        if (message.length() > 0) {
            entity.setMessage(message);
            entity.setPassword(password);
        }
    };


    private void iteratePass(char[] passChars, int currentPos, Callback<char[]> callback) {
        if (currentPos < 0) {
            return;
        }
        for (int i = 0; i < allowableChars.length; i++) {
            passChars[currentPos] = allowableChars[i];
            if (currentPos == 0) {
                callback.call(passChars);
            } else {
                iteratePass(passChars, currentPos - 1, callback);
            }
        }

    }

    private void reverseIteratePass(char[] passChars, int currentPos, Callback<char[]> callback) {
        if (currentPos < 0) {
            return;
        }
        for (int i = allowableChars.length - 1; i >= 0; i--) {
            passChars[currentPos] = allowableChars[i];
            if (currentPos == 0) {
                callback.call(passChars);
            } else {
                reverseIteratePass(passChars, currentPos - 1, callback);
            }
        }
    }

}