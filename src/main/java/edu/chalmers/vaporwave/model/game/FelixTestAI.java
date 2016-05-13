package edu.chalmers.vaporwave.model.game;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.Random;
import java.util.Set;

/**
 * Created by FEngelbrektsson on 13/05/16.
 */
public class FelixTestAI implements AI {
    private int[][] heuristicMatrix;
    private Set<GameCharacter> gameCharacterSet;
    Random r = new Random();

    public FelixTestAI(Set<GameCharacter> gameCharacterSet) {
        this.gameCharacterSet = gameCharacterSet;
    }

    public Direction getNextMove(Point enemyPosition, Point playerPosition, StaticTile[][] arenaTiles) {
        Direction nextDirection = Direction.DOWN;
        heuristicMatrix = AIHeuristics.getAIHeuristics(arenaTiles, gameCharacterSet, enemyPosition);

        int[] checkedValues = new int[4];
        checkedValues[0] = checkValueUp(enemyPosition);
        checkedValues[1] = checkValueRight(enemyPosition);
        checkedValues[2] = checkValueDown(enemyPosition);
        checkedValues[3] = checkValueLeft(enemyPosition);
        int largest = 0;
        int winner = 2;
        for (int i = 0; i < checkedValues.length; i++) {
            if(checkedValues[i] > largest) {
               largest = checkedValues[i];
                winner = i;
            }
        }

        int temp = r.nextInt(4);
        if(temp == 0) {
            nextDirection = Direction.UP;
        } else if(temp == 1) {
            nextDirection = Direction.RIGHT;
        } else if(temp == 2) {
            nextDirection = Direction.DOWN;
        } else if(temp == 3) {
            nextDirection = Direction.LEFT;
        }

        if(winner == 0) {
            nextDirection = Direction.UP;
        } else if(winner == 1) {
            nextDirection = Direction.RIGHT;
        } else if(winner == 2) {
            nextDirection = Direction.DOWN;
        } else if(winner == 3) {
            nextDirection = Direction.LEFT;
        }

        return nextDirection;
    }

    public int checkValueUp(Point enemyPosition) {
        if(enemyPosition.y < 14) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y + 1];
        } else {
            return -1;
        }
    }

    public int checkValueDown(Point enemyPosition) {
        if(enemyPosition.y > 0) {
            return heuristicMatrix[enemyPosition.x][enemyPosition.y - 1];
        } else {
            return -1;
        }
    }

    public int checkValueLeft(Point enemyPosition) {
        if(enemyPosition.x > 0) {
            return heuristicMatrix[enemyPosition.x - 1][enemyPosition.y];
        } else {
            return -1;
        }
    }

    public int checkValueRight(Point enemyPosition) {
        if(enemyPosition.x < 20) {
            return heuristicMatrix[enemyPosition.x + 1][enemyPosition.y];
        } else {
            return -1;
        }
    }


}
