package edu.chalmers.vaporwave.util;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 20/04/16.
 */
public class IOUtilitiesTest {
    private IOUtilities io = new IOUtilities();
    private char[] c;
    private File file = new File("maps/testMap.txt");

    @Test
    public void testMapReading() throws IOException {
        assertTrue(file.exists());
    }

}