package edu.chalmers.vaporwave.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by bob on 2016-04-19.
 */
public class IOUtilities {
    private char[] stringKeywords = new char[Constants.DEFAULT_GRID_WIDTH * Constants.DEFAULT_GRID_HEIGHT];
    private FileReader fr;

    public char[] readMapFile(File mapFile) {


        try {
            fr = new FileReader(mapFile);
        } catch(FileNotFoundException e) {
            System.out.println("KRASHADE VID FILEREADER SKAPNING");
        } try {
            fr.read(stringKeywords);
            fr.close();
        } catch(IOException e) {
            System.out.println("KRASHADE VID LÄSNING AV READERN");
        }
        return stringKeywords;
    }
}
