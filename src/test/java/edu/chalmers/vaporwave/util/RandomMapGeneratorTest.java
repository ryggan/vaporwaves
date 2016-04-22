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
        MapObject[][] mp = rm.generateMap();
        rm.printMap(mp);
        assertTrue(mp[0][0].equals(MapObject.EMPTY));
        }
    }