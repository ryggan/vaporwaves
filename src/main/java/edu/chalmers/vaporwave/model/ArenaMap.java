package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.MapObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ArenaMap {
    private String name;
    private MapObject[][] mapObjects;
    private Dimension mapSize;
    private Map<MapObject, Point> playersPositions;

    public ArenaMap(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = ArrayCloner.mapObjectMatrixCloner(mapObjects);
        this.mapSize = new Dimension(mapObjects.length, mapObjects[0].length);
        this.playersPositions = new HashMap<>();

        for (int i = 0; i < mapObjects[0].length; i++) {
            for (int j = 0; j < mapObjects.length; j++) {
                switch(mapObjects[j][i]) {
                    case PLAYER1:
                    case PLAYER2:
                    case PLAYER3:
                    case PLAYER4:
                        this.playersPositions.put(mapObjects[j][i], new Point(j, i));
                        break;
                }
            }
        }
    }

    public Point getSpawnPosition(MapObject mapObject) {
        return this.playersPositions.get(mapObject);
    }

    public String getName() {
        return this.name;
    }

    public MapObject[][] getMapObjects() {
        return ArrayCloner.mapObjectMatrixCloner(this.mapObjects);
    }

    public Dimension getMapSize() {
        return this.mapSize;
    }

    public String toString() {
        String mapAsString = "";
        for (int i = 0; i < this.mapObjects[0].length; i++) {
            for (int j = 0; j < this.mapObjects.length; j++) {
                mapAsString += mapObjects[j][i].toString().substring(0,1) + "  ";
            }
            mapAsString += "\n";
        }
        return mapAsString;
    }
}
