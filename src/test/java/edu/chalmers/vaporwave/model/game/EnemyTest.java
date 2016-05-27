package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {

    @Test
    public void testGetAI() throws Exception {
        Enemy enemy = new Enemy("bitchTit", 0, 0, 0, new StupidAI());
        assertTrue(enemy.getAI() instanceof StupidAI);
    }
}