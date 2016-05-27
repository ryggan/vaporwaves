package edu.chalmers.vaporwave.model.game;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class EnemyTest {

    @Test
    public void testGetAI() throws Exception {
        Set<GameCharacter> gameCharacters = new HashSet<>();
        GameCharacter gameChar = new GameCharacter();
        gameCharacters.add(gameChar);
        Enemy enemy = new Enemy("SuperEnemy", 0, 0, 0, new SemiSmartAI(gameCharacters));
        assertTrue(enemy.getAI() instanceof SemiSmartAI);
    }
}