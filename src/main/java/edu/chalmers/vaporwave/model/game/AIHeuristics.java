package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.game.PowerUp;
import edu.chalmers.vaporwave.model.game.StaticTile;
import edu.chalmers.vaporwave.model.game.Wall;
import edu.chalmers.vaporwave.util.ArrayCloner;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class AIHeuristics {
    private static int[][] heuristicMatrix = new int[21][15];

    private static void decideHeuristicValue(int[][] heuristicMatrix, Point position, int previousValue) {
        int x = position.x;
        int y = position.y;

        if(x > 0 && y > 0 && x < 20 && y < 14) {
            heuristicMatrix[x][y] = previousValue;
            previousValue = 90;
            heuristicMatrix[x - 1][y] = previousValue;
            heuristicMatrix[x + 1][y] = previousValue;
            heuristicMatrix[x][y - 1] = previousValue;
            heuristicMatrix[x][y + 1] = previousValue;
        }

        if(x > 1 && y > 1 && x < 19 && y < 13) {
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

        if(x > 2 && y > 2 && x < 18 && y < 12) {
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

    public static void setHeuristicValue(Point point, int value) {
        int x = point.x;
        int y = point.y;
        heuristicMatrix[x][y] = value;
    }

    public static int[][] getAIHeuristics(StaticTile[][] arenaTiles, Set<GameCharacter> gameCharacters, Point enemyPosition) {
        for(int i = 0; i < arenaTiles.length; i++) {
            for(int j = 0; j < arenaTiles[0].length; j++) {
                    heuristicMatrix[i][j] = 10;
            }
        }

        //set up a meeting point in center
        //recursive(new Point(11,8), 80);

        for (GameCharacter gameCharacter : gameCharacters) {
            recursive(gameCharacter.getGridPosition(), 100);
            //decideHeuristicValue(heuristicMatrix, gameCharacter.getGridPosition(), 100);
        }

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[j].length; j++) {
                if (arenaTiles[i][j] instanceof Wall) {
                    heuristicMatrix[i][j] = 0;
                }
            }
        }


        heuristicMatrix[enemyPosition.x][enemyPosition.y] = 1;

/*        System.out.println("Current boardstate");
        System.out.println("length " + heuristicMatrix.length);
        System.out.println("[] " + heuristicMatrix[0].length);
        for (int i = 0; i < heuristicMatrix[0].length ; i++) {
            for (int j = 0; j < heuristicMatrix.length ; j++) {
                System.out.print(heuristicMatrix[j][i] + " ");
            }
            System.out.println();
        }*/
        return ArrayCloner.intMatrixCloner(heuristicMatrix);
    }


    //For testing purposes
    public static int[] getSpecificHeuristics(Point enemyPosition) {
        int[] heuristicValues = new int[4];
        int x = enemyPosition.x;
        int y = enemyPosition.y;

        if(x > 0) {
            heuristicValues[0] = heuristicMatrix[x - 1][y];
        }
        if(y < 14) {
            heuristicValues[0] = heuristicMatrix[x][y + 1];
        }
        if(x < 20) {
            heuristicValues[0] = heuristicMatrix[x + 1][y];
        }
        if(y > 0) {
            heuristicValues[0] = heuristicMatrix[x][y - 1];
        }
        return ArrayCloner.intArrayCloner(heuristicValues);
    }



/*    public void setRecursiveHeuristicMatrix(ArrayList<Movable> gameCharacters) {
        for(Movable movable : gameCharacters) {
            if(movable instanceof GameCharacter) {
                recursive(movable.getGridPosition(), 100);
            }
        }
    }*/

    public static void recursive(Point playerPosition, int startValue) {
        int x = playerPosition.x;
        int y = playerPosition.y;
        heuristicMatrix[x][y] = startValue;
        recursiveLeft(x-1, y, 90);
        recursiveRight(x+1, y, 90);
        recursiveDown(x, y-1, 90);
        recursiveUp(x, y+1, 90);

        recursiveSW(x-1, y-1, 80);
        recursiveSE(x+1, y-1, 80);
        recursiveNE(x+1, y+1, 80);
        recursiveNW(x-1, y+1, 80);

    }

    public static void recursiveLeft(int x, int y, int tileValue) {
        if(x > 0 && tileValue > 0) {
            heuristicMatrix[x][y] = tileValue;
            recursiveLeft(x - 1, y, tileValue - 10);
            int z = x-1;
            recursiveNW(z-1, y+1, tileValue-10);
            recursiveSW(z-1, y-1, tileValue-10);
        }
    }

    public static void recursiveUp(int x, int y, int tileValue) {
        if(y < 14 && tileValue > 0) {
            heuristicMatrix[x][y] = tileValue;
            recursiveUp(x, y + 1, tileValue - 10);
            int z = y +1;
            recursiveNE(x+1, z+1, tileValue-10);
            recursiveNW(x-1, z+1, tileValue-10);
        }
    }

    public static void recursiveRight(int x, int y, int tileValue) {
        if(x < 20 && tileValue > 0) {
            heuristicMatrix[x][y] = tileValue;
            recursiveRight(x + 1, y, tileValue - 10);
            int z = x + 1;
            recursiveNE(z+1, y+1, tileValue-10);
            recursiveSE(z+1, y-1, tileValue-10);
        }
    }

    public static void recursiveDown(int x, int y, int tileValue) {
        if(y > 0 && tileValue > 0) {
            heuristicMatrix[x][y] = tileValue;
            recursiveDown(x, y-1, tileValue - 10);
            int z = y-1;
            recursiveSW(x-1,z-1, tileValue-10);
            recursiveSE(x+1,z-1, tileValue-10);
        }
    }

    public static void recursiveNW(int x, int y, int tileValue) {
        if(x > 0 && y < 14 && tileValue > 10) {
            heuristicMatrix[x][y] = tileValue;
            recursiveNW(x - 1, y + 1, tileValue - 10);
        }
    }

    public static void recursiveNE(int x, int y, int tileValue) {
        if(x < 20 && y < 14 && tileValue > 10) {
            heuristicMatrix[x][y] = tileValue;
            recursiveNE(x + 1, y + 1, tileValue - 10);
        }
    }

    public static void recursiveSE(int x, int y, int tileValue) {
        if(x < 20 && y > 0 && tileValue > 10) {
            heuristicMatrix[x][y] = tileValue;
            recursiveNW(x + 1, y - 1, tileValue - 10);
        }
    }

    public static void recursiveSW(int x, int y, int tileValue) {
        if(x > 0 && y > 0 && tileValue > 10) {
            heuristicMatrix[x][y] = tileValue;
            recursiveNW(x - 1, y - 1, tileValue - 10);
        }
    }
}
