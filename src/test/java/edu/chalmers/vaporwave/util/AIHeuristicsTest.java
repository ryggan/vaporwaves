package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.game.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AIHeuristicsTest {

    @Test
    public void heuristicTest() {
        StaticTile[][] sTiles = new StaticTile[Constants.GAME_WIDTH][Constants.GAME_HEIGHT];
        Set<GameCharacter> charSet = new HashSet<>();
        charSet.add(new GameCharacter("Pirre", 0));
        Set<Enemy> enemies = new HashSet<>();

        for(int i = 0; i < sTiles.length; i++) {
            for(int j = 0; j < sTiles[0].length; j++) {
                sTiles[i][j] = new IndestructibleWall();
            }
        }

        AIHeuristics aih = new AIHeuristics();
        assertTrue(aih.getAIHeuristics(sTiles, charSet, enemies)[0][1] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet, enemies)[1][0] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet, enemies)[1][1] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet, enemies)[0][0] == 0);
    }
}