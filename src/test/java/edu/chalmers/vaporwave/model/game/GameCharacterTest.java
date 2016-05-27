package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameCharacterTest {

    @Test
    public void testPlaceBomb() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setMaxBombCount(1);
        gameCharacter.setCurrentBombCount(1);
        gameCharacter.placeBomb();
        assertTrue(gameCharacter.getCurrentBombCount() == 0);
    }

    @Test
    public void testGetHealth() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setHealth(5);
        assertTrue(gameCharacter.getHealth() == 5);
    }

    @Test
    public void testGetBombRange() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setBombRange(2);
        assertTrue(gameCharacter.getBombRange() == 2);
    }


    @Test
    public void testGetBombCount() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setCurrentBombCount(5);
        assertTrue(gameCharacter.getCurrentBombCount() == 5);
    }
}