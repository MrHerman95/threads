package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by Herman Bocharov on 10.06.2017.
 */
public class NewThread implements Callable {

    static protected Logger LOGGER = Logger.getLogger(NewThread.class.getName());

    private String readFileName;
    private String writeFileName;
    private ReadWriteCharacters.TypesOfCharacters typesOfCharacters;

    NewThread(String readFileName, String writeFileName, ReadWriteCharacters.TypesOfCharacters typeOfCharacters) {
        this.readFileName = readFileName;
        this.writeFileName = writeFileName;
        this.typesOfCharacters = typeOfCharacters;
    }

    public String call() {

        LOGGER.log(Level.INFO, format("Thread %s ran... \n", Thread.currentThread().getName()));

        long startTime = System.currentTimeMillis();

        ReadWriteCharacters rWCharacters = new ReadWriteCharacters();
        int quantityOfCharacters = rWCharacters.readWriteCharacters(readFileName, writeFileName, typesOfCharacters);

        long timeSpent = System.currentTimeMillis() - startTime;

        LOGGER.log(Level.INFO, format("Thread %s finished. \n", Thread.currentThread().getName()));

        return Thread.currentThread().getName() + ": Quantity of characters = " + quantityOfCharacters +
                "; Runtime = " + timeSpent + "ms.\n";
    }

    public static class StatisticsThread implements Runnable {

        private String name;
        private String writeFileName;
        private String statistics;
        Thread t;

        StatisticsThread(String threadName, String writeFileName, String statistics) {

            name = threadName;
            t = new Thread(this, name);
            LOGGER.log(Level.INFO, "New Thread: " + t);
            this.writeFileName = writeFileName;
            this.statistics = statistics;
            t.start();

        }

        public void run() {
            try {
                FileWriter fileWriter = new FileWriter(writeFileName, false);
                fileWriter.write(statistics);
                fileWriter.flush();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}

