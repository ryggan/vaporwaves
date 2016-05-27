package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameCharacterTest {
    private GameCharacter gameCharacter = new GameCharacter("ALYSSA", 0);

    @Test
    public void testPlaceBomb() throws Exception {
        int i = gameCharacter.getCurrentBombCount();
        gameCharacter.placeBomb();
        assertTrue(gameCharacter.getCurrentBombCount() == i-1);
    }

    @Test
    public void testGetHealth() throws Exception {
        gameCharacter.setHealth(5);
        assertTrue(gameCharacter.getHealth() == 5);
    }

    @Test
    public void testGetBombRange() throws Exception {
        gameCharacter.setBombRange(2);
        assertTrue(gameCharacter.getBombRange() == 2);
    }


    @Test
    public void testGetBombCount() throws Exception {
        gameCharacter.setCurrentBombCount(5);
        assertTrue(gameCharacter.getCurrentBombCount() == 5);
    }
}