package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.ArrayCloner;

import java.awt.*;
import java.util.Set;

public class AIHeuristics {
    private static int[][] heuristicMatrix = new int[21][15];

    public static void setHeuristicValue(Point point, int value) {
        int x = point.x;
        int y = point.y;
        heuristicMatrix[x][y] = value;
    }

    public static int[][] getAIHeuristics(StaticTile[][] arenaTiles, Set<GameCharacter> gameCharacters, Set<Enemy> enemies) {
        for(int i = 0; i < arenaTiles.length; i++) {
            for(int j = 0; j < arenaTiles[0].length; j++) {
                    heuristicMatrix[i][j] = 10;
            }
        }

        recursive(new Point(11,8), 200, 10);

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof PowerUp) {
                    recursive(new Point(i,j), 100, 10);
                }
            }
        }

        for (GameCharacter gameCharacter : gameCharacters) {
            recursive(gameCharacter.getGridPosition(), 200, 10);
            //decideHeuristicValue(heuristicMatrix, gameCharacter.getGridPosition(), 100);
        }

        for (Enemy enemy : enemies) {
            recursive(enemy.getGridPosition(), 9, 5);
        }

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof Explosive) {
                    recursive(new Point(i,j), 3, 1);
                }
            }
        }

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof Wall) {
                    heuristicMatrix[i][j] = 0;
                }
            }
        }

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

    public static void recursive(Point playerPosition, int startValue, int difference) {
        int x = playerPosition.x;
        int y = playerPosition.y;
        heuristicMatrix[x][y] = startValue;
        recursiveLeft(x-1, y, startValue - difference, difference);
        recursiveRight(x+1, y, startValue - difference, difference);
        recursiveDown(x, y-1, startValue - difference, difference);
        recursiveUp(x, y+1, startValue - difference, difference);

/*        recursiveSW(x-1, y-1, startValue - 30);
        recursiveSE(x+1, y-1, startValue - 30);
        recursiveNE(x+1, y+1, startValue - 30);
        recursiveNW(x-1, y+1, startValue - 30);*/

    }

    public static void recursiveLeft(int x, int y, int tileValue, int difference) {
        if(x > 0 && tileValue > difference) {
            heuristicMatrix[x][y] = tileValue;
            recursiveLeft(x - 1, y, tileValue - difference, difference);
/*            int z = x-1;
            recursiveNW(z-1, y+1, tileValue-15);
            recursiveSW(z-1, y-1, tileValue-15);*/
        }
    }

    public static void recursiveUp(int x, int y, int tileValue, int difference) {
        if(y < 14 && tileValue > difference) {
            heuristicMatrix[x][y] = tileValue;
            recursiveUp(x, y + 1, tileValue - difference, difference);

            recursiveLeft(x-1,y, tileValue-difference, difference);
            recursiveRight(x+1,y,tileValue-difference, difference);
/*            int z = y +1;
            recursiveNE(x+1, z+1, tileValue-15);
            recursiveNW(x-1, z+1, tileValue-15);*/
        }
    }

    public static void recursiveRight(int x, int y, int tileValue, int difference) {
        if(x < 20 && tileValue > difference) {
            heuristicMatrix[x][y] = tileValue;
            recursiveRight(x + 1, y, tileValue - difference, difference);
/*            int z = x + 1;
            recursiveNE(z+1, y+1, tileValue-15);
            recursiveSE(z+1, y-1, tileValue-15);*/
        }
    }

    public static void recursiveDown(int x, int y, int tileValue, int difference) {
        if(y > 0 && tileValue > 10) {
            heuristicMatrix[x][y] = tileValue;
            recursiveLeft(x-1,y, tileValue-difference, difference);
            recursiveRight(x+1,y,tileValue-difference, difference);
            recursiveDown(x, y-1, tileValue - difference, difference);


/*            int z = y-1;
            recursiveSW(x-1,z-1, tileValue-15);
            recursiveSE(x+1,z-1, tileValue-15);*/
        }
    }

/*    public static void recursiveNW(int x, int y, int tileValue) {
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
    }*/
}
