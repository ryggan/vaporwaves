package edu.chalmers.vaporwave.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by FEngelbrektsson on 21/04/16.
 *
 *
 * Generates a 16x14 map
 */
public class RandomMapGenerator {
    private Random random;
    private int numberOfRows = 14;
    private int numberOfColumns = 16;

    public RandomMapGenerator() {
        random = new Random(9);
    }

    public MapObject[][] generateMap() {
        System.out.println("1");
        MapObject[][] mapObjects = new MapObject[numberOfRows][numberOfColumns];
        for(int i = 0; i < numberOfRows; i++) {
            System.out.println("2");
            for(int j = 0; j < numberOfColumns; j++){
                System.out.println("3");
                if (random.nextInt() == 1 || random.nextInt() == 2) {
                    mapObjects[i][j] = MapObject.INDESTRUCTIBLE_WALL;
                } else if (random.nextInt() == 3 || random.nextInt() == 4 || random.nextInt() == 5) {
                    mapObjects[i][j] = MapObject.INDESTRUCTIBLE_WALL;
                } else {
                    mapObjects[i][j] = MapObject.EMPTY;
                }
            }
        }
        System.out.println("Starting to place spawnpoints");
        /**
         * Player 1 spawnpoint
         */
        mapObjects[0][0] = MapObject.EMPTY;
        mapObjects[0][1] = MapObject.EMPTY;
        mapObjects[1][0] = MapObject.EMPTY;

        /**
         * Player 2 spawnpoint
         */
        System.out.println("The system is broken by 15");
        mapObjects[0][15] = MapObject.EMPTY;
        System.out.println("Just joking kitty");
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

        System.out.println("Returning the mapobject");
        return mapObjects;
    }

    public void printMap(MapObject[][] mapObjects) {
        System.out.println("<<<< CURRENT MAP >>>>");
        for(int i = 0; i < mapObjects.length; i++) {
            System.out.println();
            for(int j = 0; j < mapObjects[0].length; j++) {
                switch(mapObjects[i][j]) {
                    case DESTRUCTIBLE_WALL:
                        System.out.print("D ");
                    case INDESTRUCTIBLE_WALL:
                        System.out.print("X ");
                    case EMPTY:
                        System.out.print("° ");
                }
            }
        }
    }
}
