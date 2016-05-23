package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.ArrayCloner;

import java.awt.*;
import java.util.Set;

public class AIHeuristics {
    private static int[][] heuristicMatrix = new int[21][15];

// Not in use atm
//    private static void decideHeuristicValue(int[][] heuristicMatrix, Point position, int previousValue) {
//        int x = position.x;
//        int y = position.y;
//
//        if(x > 0 && y > 0 && x < 20 && y < 14) {
//            heuristicMatrix[x][y] = previousValue;
//            previousValue = 90;
//            heuristicMatrix[x - 1][y] = previousValue;
//            heuristicMatrix[x + 1][y] = previousValue;
//            heuristicMatrix[x][y - 1] = previousValue;
//            heuristicMatrix[x][y + 1] = previousValue;
//        }
//
//        if(x > 1 && y > 1 && x < 19 && y < 13) {
//            previousValue = 80;
//            heuristicMatrix[x - 2][y] = previousValue;
//            heuristicMatrix[x - 1][y + 1] = previousValue;
//            heuristicMatrix[x - 1][y - 1] = previousValue;
//
//            heuristicMatrix[x + 2][y] = previousValue;
//            heuristicMatrix[x + 1][y + 1] = previousValue;
//            heuristicMatrix[x + 1][y - 1] = previousValue;
//
//            heuristicMatrix[x][y + 2] = previousValue;
//            heuristicMatrix[x - 1][y + 1] = previousValue;
//            heuristicMatrix[x + 1][y + 1] = previousValue;
//
//            heuristicMatrix[x][y - 2] = previousValue;
//            heuristicMatrix[x - 1][y - 1] = previousValue;
//            heuristicMatrix[x + 1][y - 1] = previousValue;
//        }
//
//        if(x > 2 && y > 2 && x < 18 && y < 12) {
//            previousValue = 70;
//            heuristicMatrix[x - 3][y] = previousValue;
//            heuristicMatrix[x - 2][y + 1] = previousValue;
//            heuristicMatrix[x - 2][y - 1] = previousValue;
//
//            heuristicMatrix[x + 3][y] = previousValue;
//            heuristicMatrix[x + 2][y + 1] = previousValue;
//            heuristicMatrix[x + 2][y - 1] = previousValue;
//
//            heuristicMatrix[x][y + 3] = previousValue;
//            heuristicMatrix[x - 1][y + 2] = previousValue;
//            heuristicMatrix[x + 1][y + 2] = previousValue;
//
//            heuristicMatrix[x][y - 3] = previousValue;
//            heuristicMatrix[x - 1][y - 2] = previousValue;
//            heuristicMatrix[x + 1][y - 2] = previousValue;
//        }
//    }

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

        //set up a meeting point in center
        //recursive(new Point(11,8), 80);

        recursive(new Point(11,8), 200, 10, true, 10);

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof PowerUp) {
                    recursive(new Point(i,j), 100, 10, true, 10);
                }
            }
        }

        for (GameCharacter gameCharacter : gameCharacters) {
            recursive(gameCharacter.getGridPosition(), 200, 10, true, 10);
            //decideHeuristicValue(heuristicMatrix, gameCharacter.getGridPosition(), 100);
        }

        for (Enemy enemy : enemies) {
            recursive(enemy.getGridPosition(), 1, -1, false, 4);
        }

        for(int i = 0; i < arenaTiles.length; i++) {
            for (int j = 0; j < arenaTiles[0].length; j++) {
                if (arenaTiles[i][j] instanceof Explosive) {
                    recursive(new Point(i,j), 0, -2, false, 10);
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


       // heuristicMatrix[enemyPreviousPosition.x][enemyPreviousPosition.y] = -1;

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

    public static void recursive(Point playerPosition, int startValue, int difference, boolean isGreater, int stopNr) {
        int x = playerPosition.x;
        int y = playerPosition.y;
        heuristicMatrix[x][y] = startValue;
        recursiveLeft(x-1, y, startValue - difference, difference, isGreater, stopNr);
        recursiveRight(x+1, y, startValue - difference, difference, isGreater, stopNr);
        recursiveDown(x, y-1, startValue - difference, difference, isGreater, stopNr);
        recursiveUp(x, y+1, startValue - difference, difference, isGreater, stopNr);
    }

    public static void recursiveLeft(int x, int y, int tileValue, int difference, boolean isGreater, int stopNr) {
        if(isGreater) {
            if (x > 0 && tileValue > stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        } else {
            if (x > 0 && tileValue < stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        }
    }

    public static void recursiveUp(int x, int y, int tileValue, int difference, boolean isGreater, int stopNr) {
        if(isGreater) {
            if (y < 14 && tileValue > stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveUp(x, y + 1, tileValue - difference, difference, isGreater, stopNr);

                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        } else {
            if (y < 14 && tileValue < stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveUp(x, y + 1, tileValue - difference, difference, isGreater, stopNr);

                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        }
    }

    public static void recursiveRight(int x, int y, int tileValue, int difference, boolean isGreater, int stopNr) {
        if(isGreater) {
            if (x < 20 && tileValue > stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        } else {
            if (x < 20 && tileValue < stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
            }
        }
    }

    public static void recursiveDown(int x, int y, int tileValue, int difference, boolean isGreater, int stopNr) {
        if(isGreater) {
            if (y > 0 && tileValue > stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveDown(x, y - 1, tileValue - difference, difference, isGreater, stopNr);
            }
        } else {
            if (y > 0 && tileValue < stopNr) {
                heuristicMatrix[x][y] = tileValue;
                recursiveLeft(x - 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveRight(x + 1, y, tileValue - difference, difference, isGreater, stopNr);
                recursiveDown(x, y - 1, tileValue - difference, difference, isGreater, stopNr);
            }
        }
    }

}
