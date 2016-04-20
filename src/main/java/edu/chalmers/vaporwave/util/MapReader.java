package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.gameObjects.Tile;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by FEngelbrektsson on 20/04/16.
 */
public class MapReader {
    private String[][] stringKeywords = new String[Constants.DEFAULT_GRID_HEIGHT][Constants.DEFAULT_GRID_WIDTH];
    private Scanner textScanner;
    private String[] linesHolder = new String[Constants.DEFAULT_GRID_HEIGHT];
   // private String[] parsedLines;


    /**
     *
     * @param mapFile
     * @return String array of the lines read from the file
     * @throws Exception
     */
    public String[] readMapRows(File mapFile) throws Exception {
        textScanner = new Scanner(mapFile);
        int i = 0;
        while(textScanner.hasNextLine()) {
            linesHolder[i] = textScanner.nextLine();
            i++;
        }
        return linesHolder;
    }

    /**
     *
     * @param strings takes an array of strings and parses the characters
     * @return matrix of the characters, in string format
     */
    public String[][] parseStrings(String[] strings) {
        for(int i = 0; i < strings.length; i++) {
               stringKeywords[i] = strings[i].split(" ");
        }
        return stringKeywords;
    }

    /**
     *
     * @param mapFile
     * @return a matrix of the characters, read from the mapFile, in string format
     * @throws Exception
     */
    public String[][] createMapArray(File mapFile) throws Exception {
       linesHolder = readMapRows(mapFile);
        return parseStrings(linesHolder);
    }

    public Boolean isDestructibleWall(char c) {
        if(c == 'X') {
            return true;
        }
        return false;
    }

    public Boolean isInDestructibleWall(char c) {
        if(c == 'O') {
            return true;
        }
        return false;
    }

    public Boolean isEnemySpawn(char c) {
        if(c == 'E') {
            return true;
        }
        return false;
    }

    public Boolean isCharacterSpawn(char c) {
        if(c == 'C') {
            return true;
        }
        return false;
    }

}
