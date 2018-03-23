package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Herman Bocharov on 10.06.2017.
 */
public class ReadWriteCharacters {

    static protected Logger LOGGER = Logger.getLogger(ReadWriteCharacters.class.getName());

    public enum TypesOfCharacters {
        NUMBERS, LETTERS, SPECIAL_CHARACTERS
    }

    public void writeRandomCharacters(String writeFileName) {

        Random randomNumber = new Random();
        int probability;

        int size = randomNumber.nextInt(1000000);
        char[] characters = new char[size];

        for (int i = 0; i < characters.length; i++) {
            probability = randomNumber.nextInt(25);
            if (probability == 10) {
                // Probability of ASCII line feed character equals to 0.04
                characters[i] = (char) 10;
            } else {
                // Random ASCII printable characters from 32 to 126
                characters[i] = (char) (32 + randomNumber.nextInt(95));
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(writeFileName, false);
            fileWriter.write(characters);
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public int readWriteCharacters(String readFileName, String writeFileName, TypesOfCharacters typeOfCharacters) {

        int quantityOfCharacters = 0;

        try {
            FileReader fileReader = new FileReader(readFileName);
            FileWriter fileWriter = new FileWriter(writeFileName, false);
            int c;
            if (typeOfCharacters == TypesOfCharacters.NUMBERS) {
                while ((c = fileReader.read()) != -1) {
                    if (Character.isDigit(c)) {
                        fileWriter.write(c);
                        quantityOfCharacters++;
                    }
                }
            } else if (typeOfCharacters == TypesOfCharacters.LETTERS) {
                while ((c = fileReader.read()) != -1) {
                    if (Character.isLetter(c)) {
                        fileWriter.write(c);
                        quantityOfCharacters++;
                    }
                }
            } else if (typeOfCharacters == TypesOfCharacters.SPECIAL_CHARACTERS) {
                while ((c = fileReader.read()) != -1) {
                    if ( (!Character.isDigit(c)) && (!Character.isLetter(c)) ) {
                        fileWriter.write(c);
                        quantityOfCharacters++;
                    }
                }
            }
            fileWriter.flush();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return quantityOfCharacters;
    }
}