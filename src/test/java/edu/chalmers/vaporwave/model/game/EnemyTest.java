package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.StupidAI;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class EnemyTest {

    @Test
    public void testGetAI() throws Exception {
        Enemy enemy = new Enemy("bitchTit", 0, 0, 0, new StupidAI());
        assertTrue(enemy.getAI() instanceof StupidAI);
    }
}