package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExplosiveTest {

    @Test
    public void explosiveTest() {
        GameCharacter gameCharacter = new GameCharacter();
        Explosive bomb = new Bomb(gameCharacter, 10, 0, 10, 30);
        assertTrue(gameCharacter.getGridPosition().equals(bomb.getPosition()));
    }
}