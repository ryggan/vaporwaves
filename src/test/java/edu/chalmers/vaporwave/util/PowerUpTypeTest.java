package edu.chalmers.vaporwave.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PowerUpTypeTest {

    @Test
    public void testGetSpawnChance() throws Exception {
        assertTrue(PowerUpType.getSpawnChance(PowerUpType.BOMB_COUNT) == 1);
        assertTrue(PowerUpType.getSpawnChance(PowerUpType.HEALTH) == 1);
        assertTrue(PowerUpType.getSpawnChance(PowerUpType.RANGE) == 1);
        assertTrue(PowerUpType.getSpawnChance(PowerUpType.SPEED) == 1);
    }
}