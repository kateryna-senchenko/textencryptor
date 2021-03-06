package com.javaclasses.encryptor.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.javaclasses.encryptor.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of the TextEncryptor interface
 */
public class TextEncryptorImpl implements TextEncryptor {

    private static final Logger log = LoggerFactory.getLogger(TextEncryptorImpl.class);

    @Override
    public String execute(String text) {

        checkNotNull(text, "Input should not be null");

        text = text.replaceAll("\\s", "");

        if (text.isEmpty()){
            log.error("Empty input");
            throw new IllegalStateException("Input should contain text");
        }

        final char[][] grid = checkNotNull(populateGrid(text),
                "Populated grid should not be null");

        if (log.isInfoEnabled()){
            log.info("Grid has been populated with characters");
        }

        final String encryptedText = checkNotNull(formEncryptedText(grid),
                "Encrypted text should not be null");

        if (log.isInfoEnabled()){
            log.info("Text has been encrypted");
        }

        return encryptedText;
    }

    /**
     * populates grid with characters
     * @param text - text to be split into characters to populate the grid
     * @return 2d array containing the characters from the text
     */
    private char[][] populateGrid(String text){

        final int length = text.length();

        int rows = (int)Math.floor(Math.sqrt(length));
        final int columns = (int)Math.ceil(Math.sqrt(length));


        if (rows * columns < length){
            rows = columns;
        }

        final char[][] grid = new char[rows][columns];

        if (log.isInfoEnabled()){
            log.info("Created grid with " + rows + " rows and " + columns + " columns");
        }


        for (int i=0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if ((i * columns + j) < length) {
                    grid[i][j] = text.charAt(i * columns + j);

                    if(log.isDebugEnabled()){
                        log.debug("Character " + grid[i][j] + " written into the grid");
                    }
                }
            }
        }
        return grid;
    }

    /**
     * forms the encrypted text
     * @param grid - 2d array populated with characters from text to be encrypted
     * @return encrypted text
     */
    private String formEncryptedText(char[][] grid){

        final StringBuilder encryptedText = new StringBuilder();

        final int rows = grid.length;
        final int columns = grid[0].length;

        for (int i=0; i < columns; i++){

            if(i != 0) {
                encryptedText.append(" ");
            }

            for (int j = 0; j < rows; j++){

                if (grid[j][i] != 0) {
                    encryptedText.append(grid[j][i]);

                    if(log.isDebugEnabled()){
                        log.debug("Appended character " + grid[j][i]);
                    }
                }
            }
        }

        return encryptedText.toString();
    }


}
