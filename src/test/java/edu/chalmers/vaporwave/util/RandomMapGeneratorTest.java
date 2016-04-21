package edu.chalmers.vaporwave.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 21/04/16.
 */
public class RandomMapGeneratorTest {

    @Test
    public void testRandomMapGenerator() {
        RandomMapGenerator rm = new RandomMapGenerator();
        rm.printMap(rm.generateMap());
        assertTrue(rm.generateMap()[0][0] == MapObject.EMPTY);
    }
}