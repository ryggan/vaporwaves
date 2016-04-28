package edu.chalmers.vaporwave.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomMapGenerator {
    private Random random;
    private int numberOfRows = 14;
    private int numberOfColumns = 16;

    public RandomMapGenerator() {
        random = new Random();
    }

    public MapObject[][] generateMap() {
        MapObject[][] mapObjects = new MapObject[numberOfRows][numberOfColumns];
        int randomNumber = 0;
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++){
                randomNumber = random.nextInt(9);
                if (randomNumber == 1 || randomNumber == 2) {
                    mapObjects[i][j] = MapObject.INDESTRUCTIBLE_WALL;
                } else if (randomNumber == 3 || randomNumber == 4 || randomNumber == 5) {
                    mapObjects[i][j] = MapObject.DESTRUCTIBLE_WALL;
                } else {
                    mapObjects[i][j] = MapObject.EMPTY;
                }
            }
        }
        /**
         * Player 1 spawnpoint
         */
        mapObjects[0][0] = MapObject.EMPTY;
        mapObjects[0][1] = MapObject.EMPTY;
        mapObjects[1][0] = MapObject.EMPTY;

        /**
         * Player 2 spawnpoint
         */
        mapObjects[0][15] = MapObject.EMPTY;
        mapObjects[0][14] = MapObject.EMPTY;
        mapObjects[1][15] = MapObject.EMPTY;

        /**
         * Player 3 spawnpoint
         */
        mapObjects[13][0] = MapObject.EMPTY;
        mapObjects[13][1] = MapObject.EMPTY;
        mapObjects[12][0] = MapObject.EMPTY;

        /**
         * Player 4 spawnpoint
         */
        mapObjects[13][15] = MapObject.EMPTY;
        mapObjects[13][14] = MapObject.EMPTY;
        mapObjects[12][15] = MapObject.EMPTY;

        return mapObjects;
    }

    public void printMap(MapObject[][] mapObjects) {
        System.out.println("<<<< CURRENT MAP >>>>");

        for(int i = 0; i < mapObjects.length; i++) {
            for(int j = 0; j < mapObjects[0].length; j++) {

                switch(mapObjects[i][j]) {
                    case DESTRUCTIBLE_WALL:
                        System.out.print("D ");
                        break;
                    case INDESTRUCTIBLE_WALL:
                        System.out.print("X ");
                        break;
                    case EMPTY:
                        System.out.print("° ");
                        break;
                }
            }
            System.out.println();
        }
    }
}
