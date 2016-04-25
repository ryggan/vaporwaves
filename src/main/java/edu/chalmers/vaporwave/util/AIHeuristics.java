package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.PowerUp;
import edu.chalmers.vaporwave.model.gameObjects.StaticTile;
import edu.chalmers.vaporwave.model.gameObjects.Wall;

/**
 * Created by FEngelbrektsson on 25/04/16.
 */
public class AIHeuristics {
    private ArenaModel arenaModel;
    private GameCharacter[] gameCharacters;

    public AIHeuristics(ArenaModel arenaModel) {
        this.arenaModel = arenaModel;
    }

    public void decideHeuristicValue(int[][] heuristicMatrix, int x, int y, int previousValue) {
        heuristicMatrix[x][y] = previousValue;
        previousValue = 90;
        heuristicMatrix[x-1][y] = previousValue;
        heuristicMatrix[x+1][y] = previousValue;
        heuristicMatrix[x][y-1] = previousValue;
        heuristicMatrix[x][y+1] = previousValue;

        previousValue = 80;
        heuristicMatrix[x-2][y] = previousValue;
        heuristicMatrix[x-1][y+1] = previousValue;
        heuristicMatrix[x-1][y-1] = previousValue;

        heuristicMatrix[x+2][y] = previousValue;
        heuristicMatrix[x+1][y+1] = previousValue;
        heuristicMatrix[x+1][y-1] = previousValue;

        heuristicMatrix[x][y+2] = previousValue;
        heuristicMatrix[x-1][y+1] = previousValue;
        heuristicMatrix[x+1][y+1] = previousValue;

        heuristicMatrix[x][y-2] = previousValue;
        heuristicMatrix[x-1][y-1] = previousValue;
        heuristicMatrix[x+1][y-1] = previousValue;

        previousValue = 70;
        heuristicMatrix[x-3][y] = previousValue;
        heuristicMatrix[x-2][y+1] = previousValue;
        heuristicMatrix[x-2][y-1] = previousValue;

        heuristicMatrix[x+3][y] = previousValue;
        heuristicMatrix[x+2][y+1] = previousValue;
        heuristicMatrix[x+2][y-1] = previousValue;

        heuristicMatrix[x][y+3] = previousValue;
        heuristicMatrix[x-1][y+2] = previousValue;
        heuristicMatrix[x+1][y+2] = previousValue;

        heuristicMatrix[x][y-3] = previousValue;
        heuristicMatrix[x-1][y-2] = previousValue;
        heuristicMatrix[x+1][y-2] = previousValue;
    }

    public int[][] getAIHeuristics() {
        int[][] heuristicMatrix = new int[arenaModel.getWidth()][arenaModel.getHeight()];
        StaticTile[][] temporaryMatrix = arenaModel.getArenaTiles();

        for(int i = 0; i < temporaryMatrix.length; i++) {
            for(int j = 0; j < temporaryMatrix[j].length; j++) {
                if(temporaryMatrix[i][j] instanceof PowerUp) {
                    decideHeuristicValue(heuristicMatrix, i, j, 50);
                } else if(temporaryMatrix[i][j] instanceof Wall) {
                    heuristicMatrix[i][j] = 0;
                } else {
                    heuristicMatrix[i][j] = 10;
                }
            }
        }
        
        for(int k = 0; k < gameCharacters.length; k++) {
            gameCharacters[k].getGridPosition().getX();
            gameCharacters[k].getGridPosition().getY();
            decideHeuristicValue(heuristicMatrix, gameCharacters[k].getGridPosition().x, gameCharacters[k].getGridPosition().y, 100);
        }

        return heuristicMatrix;
    }
}
