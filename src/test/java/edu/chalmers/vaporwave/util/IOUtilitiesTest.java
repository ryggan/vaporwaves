package edu.chalmers.vaporwave.util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 20/04/16.
 */
public class IOUtilitiesTest {
    private IOUtilities io = new IOUtilities();
    private char[] c;
    private File file = new File("maps/testMap.txt");

    @Test
    public void testMapReading() {
        System.out.println("<<<<FELIX>>>>>>");
        //System.out.println(file);


        c = io.readMapFile(file);
        System.out.println("<<<<FELIX>>>>>>");
        System.out.println("the character is" + " " + c[1]);
        System.out.println("<<<<FELIX>>>>>>");
        assertTrue(c[1] == 'W');
    }

}