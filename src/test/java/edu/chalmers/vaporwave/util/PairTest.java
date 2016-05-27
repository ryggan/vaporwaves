package edu.chalmers.vaporwave.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 27/05/16.
 */
public class PairTest {
    private Pair<String, Integer> myPair = new Pair<>("Alyssa", 1);

    @Test
    public void testGetFirst() {
        assertTrue(myPair.getFirst() instanceof String);
        assertTrue(myPair.getFirst() == "Alyssa");
    }

    @Test
    public void testGetSecond() {
        assertTrue(myPair.getSecond() instanceof Integer);
        assertTrue(myPair.getSecond() == 1);
    }

    @Test
    public void testSetFirst() {
        myPair.setFirst("newText");
        assertTrue(myPair.getFirst() == "newText");
    }

    @Test
    public void testSetSecond() {
        myPair.setSecond(7);
        assertTrue(myPair.getSecond() == 7);
    }
}