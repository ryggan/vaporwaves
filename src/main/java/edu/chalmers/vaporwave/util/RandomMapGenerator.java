package edu.chalmers.vaporwave.util;

import java.util.Random;

/**
 * This class generates a random map to be used as arena.
 * It is not used as of now in the game, but has been saved for future use.
 */
public class RandomMapGenerator {
    private Random random;
    private int gridWidth;
    private int gridHeight;

    public RandomMapGenerator(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        random = new Random();
    }

    public RandomMapGenerator() {
        this(Constants.DEFAULT_GRID_WIDTH, Constants.DEFAULT_GRID_HEIGHT);
    }

    public MapObject[][] generateMap() {
        MapObject[][] mapObjects = new MapObject[this.gridWidth][this.gridHeight];

        for (int i = 1; i < mapObjects.length; i += 2) {
            for (int j = 1; j < mapObjects[i].length; j += 2) {
                mapObjects[i][j] = MapObject.INDESTRUCTIBLE_WALL;
            }
        }

        int randomNumber;
        for(int i = 0; i < mapObjects.length; i++) {
            for(int j = 0; j < mapObjects[i].length; j++){
                if (mapObjects[i][j] == null) {
                    randomNumber = random.nextInt(100);
                    if (randomNumber < 70) {
                        mapObjects[i][j] = MapObject.DESTRUCTIBLE_WALL;
                    } else {
                        mapObjects[i][j] = MapObject.EMPTY;
                    }
                }
            }
        }
        /**
         * Player 1 spawnpoint
         */
        int x = 0;
        int y = 0;
        mapObjects[x + 0][y + 0] = MapObject.PLAYER1;
        mapObjects[x + 1][y + 0] = MapObject.EMPTY;
        mapObjects[x + 0][y + 1] = MapObject.EMPTY;

        /**
         * Player 2 spawnpoint
         */
        x = mapObjects.length - 1;
        y = 0;
        mapObjects[x - 0][y + 0] = MapObject.PLAYER2;
        mapObjects[x - 1][y + 0] = MapObject.EMPTY;
        mapObjects[x - 0][y + 1] = MapObject.EMPTY;

        /**
         * Player 3 spawnpoint
         */
        x = 0;
        y = mapObjects[0].length - 1;
        mapObjects[x + 0][y - 0] = MapObject.PLAYER3;
        mapObjects[x + 1][y - 0] = MapObject.EMPTY;
        mapObjects[x + 0][y - 1] = MapObject.EMPTY;

        /**
         * Player 4 spawnpoint
         */
        x = mapObjects.length - 1;
        y = mapObjects[0].length - 1;
        mapObjects[x - 0][y - 0] = MapObject.PLAYER4;
        mapObjects[x - 1][y - 0] = MapObject.EMPTY;
        mapObjects[x - 0][y - 1] = MapObject.EMPTY;

        return mapObjects;
    }
}
