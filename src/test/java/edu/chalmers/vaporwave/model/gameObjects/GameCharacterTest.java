package edu.chalmers.vaporwave.model.gameObjects;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameCharacterTest {
    private GameCharacter gC = new GameCharacter("Pirre");

    @Test
    public void testPlaceBomb() throws Exception {
        int i = gC.getCurrentBombCount();
        gC.placeBomb();
        assertTrue(gC.getCurrentBombCount() == i-1);
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
        gC.setCurrentBombCount(5);
        assertTrue(gC.getCurrentBombCount() == 5);
    }
}