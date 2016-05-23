package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.game.IndestructibleWall;
import edu.chalmers.vaporwave.model.game.StaticTile;
import edu.chalmers.vaporwave.model.game.AIHeuristics;
import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class AIHeuristicsTest {

    @Test
    public void heuristicTest() {
        StaticTile[][] sTiles = new StaticTile[Constants.GAME_WIDTH][Constants.GAME_HEIGHT];
        Set<GameCharacter> charSet = new HashSet<>();
        charSet.add(new GameCharacter("Pirre", 0));

        for(int i = 0; i < sTiles.length; i++) {
            for(int j = 0; j < sTiles[0].length; j++) {
                sTiles[i][j] = new IndestructibleWall();
            }
        }

        AIHeuristics aih = new AIHeuristics();
        assertTrue(aih.getAIHeuristics(sTiles, charSet)[0][1] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet)[1][0] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet)[1][1] == 0);
        assertTrue(aih.getAIHeuristics(sTiles, charSet)[0][0] == 0);
    }
}