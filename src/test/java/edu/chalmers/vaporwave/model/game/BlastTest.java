package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlastTest {

    @Test
    public void testGetPosition() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        Explosive bomb = new Bomb(gameCharacter, 1, 1, 1, 30);
        System.out.println("BombPosition: " + bomb.getPosition());
        System.out.println("AlyssaPosition: " + gameCharacter.getGridPosition());
        assertTrue(bomb.getPosition().equals(gameCharacter.getGridPosition()));

    }

    @Test
    public void testGetRange() throws Exception {
        GameCharacter gameCharacter = new GameCharacter();
        Explosive bomb = new Bomb(gameCharacter, 1, 1, 1, 30);
        assertTrue(bomb.getRange() == 1);
    }
}