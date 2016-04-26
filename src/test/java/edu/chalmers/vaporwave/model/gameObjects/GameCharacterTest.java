package edu.chalmers.vaporwave.model.gameObjects;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class GameCharacterTest {
    private GameCharacter gC = new GameCharacter("Pirre");

    @Test
    public void testPlaceBomb() throws Exception {
        int i = gC.getBombCount();
        gC.placeBomb();
        assertTrue(gC.getBombCount() == i-1);
    }

    @Test
    public void testSetMaxHealth() throws Exception {
        gC.setMaxHealth(10);
        assertTrue(gC.getMaxHealth() == 10);
    }

    @Test
    public void testGetHealth() throws Exception {
        gC.setHealth(5);
        assertTrue(gC.getHealth() == 5);
    }


    @Test
    public void testGetBombRange() throws Exception {
        gC.setBombRange(2);
        assertTrue(gC.getBombRange() == 2);
    }


    @Test
    public void testGetBombCount() throws Exception {
        gC.setBombCount(5);
        assertTrue(gC.getBombCount() == 5);
    }
}