package ua.dudka.cracker;

import ua.dudka.coder.FileEncoder;
import ua.dudka.entity.Entity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnilateralFileCracker implements FileCracker {
    private char[] allowableChars;
    private ExecutorService executorService;
    private Entity entity;

    private String filename;

    public UnilateralFileCracker() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });


        this.entity = new Entity("", "");
        allowableChars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
    }

    @Override
    public Entity crack(String filename) {
        setFilename(filename);
        startCracking();
        waitForAnswerAndShutdown();
        return entity;
    }

    private void setFilename(String filename) {
        this.filename = filename;
    }

    private void startCracking() {
        for (int i = 1; i <= 5; i++) {
            final int passLen = i;
            executorService.submit(() -> {
                char[] pass = new char[passLen];
                iteratePass(pass, pass.length - 1, callback);
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

    private void waitForAnswerAndShutdown() {
        while (entity.getMessage().length() < 1) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdownNow();
    }

    private void iteratePass(char[] passChars, int currentPos, Callback<char[]> callback) {
        if (currentPos > -1) {
            for (char allowableChar : allowableChars) {
                passChars[currentPos] = allowableChar;
                if (currentPos == 0) {
                    callback.call(passChars);
                } else {
                    iteratePass(passChars, currentPos - 1, callback);
                }
            }
        }
    }
}