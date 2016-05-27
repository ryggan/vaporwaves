package edu.chalmers.vaporwave.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomMapGeneratorTest {

    @Test
    public void testRandomMapGenerator() {
        RandomMapGenerator rm = new RandomMapGenerator();
        MapObject[][] mp = rm.generateMap();
        assertTrue(mp[0][0].equals(MapObject.EMPTY));
        }
    }