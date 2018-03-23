package com.company;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static protected Logger LOGGER = Logger.getLogger(NewThread.class.getName());

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, "Main thread ran...");

        ReadWriteCharacters randomCharacters = new ReadWriteCharacters();
        randomCharacters.writeRandomCharacters("res\\random_characters.txt");

        ExecutorService executor = Executors.newCachedThreadPool();

        try {

            Future<String> numbers = executor.submit(new NewThread("res\\random_characters.txt",
                    "res\\only_numbers.txt", ReadWriteCharacters.TypesOfCharacters.NUMBERS));

            Future<String> letters = executor.submit(new NewThread("res\\random_characters.txt",
                    "res\\only_letters.txt", ReadWriteCharacters.TypesOfCharacters.LETTERS));

            Future<String> specialCharacters = executor.submit(new NewThread("res\\random_characters.txt",
                    "res\\only_special_characters.txt",
                     ReadWriteCharacters.TypesOfCharacters.SPECIAL_CHARACTERS));

            Future<String> stat = (Future<String>) executor.submit(new NewThread.StatisticsThread(
                    "StatisticsThread",
                    "res\\statistics.txt", numbers.get() + letters.get() + specialCharacters.get()));

        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        executor.shutdown();

        LOGGER.log(Level.INFO, "Main thread finished successfully.");
    }
}

