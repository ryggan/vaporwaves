package edu.chalmers.vaporwave.util;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.PowerUp;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;
import edu.chalmers.vaporwave.model.gameObjects.Wall;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FEngelbrektsson on 25/04/16.
 */
public class AIHeuristics {
    private Set<GameCharacter> gameCharacters;
    private StaticTile[][] arenaTiles;
    private int[][] heuristicMatrix;

    private void decideHeuristicValue(int[][] heuristicMatrix, Point position, int previousValue) {
        int x = position.x;
        int y = position.y;

        if(x > 0 && y > 0 && x < 15 && y < 13) {
            heuristicMatrix[x][y] = previousValue;
            previousValue = 90;
            if(x > 0) {
                heuristicMatrix[x - 1][y] = previousValue;
                heuristicMatrix[x + 1][y] = previousValue;
            }
            if(y > 0) {
                heuristicMatrix[x][y - 1] = previousValue;
                heuristicMatrix[x][y + 1] = previousValue;
            }
        }

        if(x > 1 && y > 1 && x < 14 && y < 12) {
            previousValue = 80;
            heuristicMatrix[x - 2][y] = previousValue;
            heuristicMatrix[x - 1][y + 1] = previousValue;
            heuristicMatrix[x - 1][y - 1] = previousValue;

            heuristicMatrix[x + 2][y] = previousValue;
            heuristicMatrix[x + 1][y + 1] = previousValue;
            heuristicMatrix[x + 1][y - 1] = previousValue;

            heuristicMatrix[x][y + 2] = previousValue;
            heuristicMatrix[x - 1][y + 1] = previousValue;
            heuristicMatrix[x + 1][y + 1] = previousValue;

            heuristicMatrix[x][y - 2] = previousValue;
            heuristicMatrix[x - 1][y - 1] = previousValue;
            heuristicMatrix[x + 1][y - 1] = previousValue;
        }

        if(x > 2 && y > 2 && x < 13 && y < 11) {
            previousValue = 70;
            heuristicMatrix[x - 3][y] = previousValue;
            heuristicMatrix[x - 2][y + 1] = previousValue;
            heuristicMatrix[x - 2][y - 1] = previousValue;

            heuristicMatrix[x + 3][y] = previousValue;
            heuristicMatrix[x + 2][y + 1] = previousValue;
            heuristicMatrix[x + 2][y - 1] = previousValue;

            heuristicMatrix[x][y + 3] = previousValue;
            heuristicMatrix[x - 1][y + 2] = previousValue;
            heuristicMatrix[x + 1][y + 2] = previousValue;

            heuristicMatrix[x][y - 3] = previousValue;
            heuristicMatrix[x - 1][y - 2] = previousValue;
            heuristicMatrix[x + 1][y - 2] = previousValue;
        }
    }

    public AIHeuristics(StaticTile[][] arenaTiles, Set<GameCharacter> gameCharacters) {
        this.arenaTiles = arenaTiles;
        this.gameCharacters = gameCharacters;
        heuristicMatrix = new int[this.arenaTiles.length][this.arenaTiles[0].length];

    }

    public void setHeuristicValue(Point point, int value) {
        int x = point.x;
        int y = point.y;
        heuristicMatrix[x][y] = value;
    }

    public int[][] getAIHeuristics() {
        for(int i = 0; i < arenaTiles.length; i++) {
            for(int j = 0; j < arenaTiles[j].length; j++) {
                if(arenaTiles[i][j] instanceof PowerUp) {
                    decideHeuristicValue(heuristicMatrix, new Point(i, j), 50);
                } else if(arenaTiles[i][j] instanceof Wall) {
                    heuristicMatrix[i][j] = 0;
                } else {
                    heuristicMatrix[i][j] = 10;
                }
            }
        }

        for (GameCharacter gameCharacter : gameCharacters) {
            decideHeuristicValue(heuristicMatrix, gameCharacter.getGridPosition(), 100);
        }


        return heuristicMatrix;
    }
}
