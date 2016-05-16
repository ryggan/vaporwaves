package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.MapObject;

import java.awt.*;

public class ArenaMap {
    private String name;
    private MapObject[][] mapObjects;
    private Dimension mapSize;

    public ArenaMap(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = ArrayCloner.mapObjectMatrixCloner(mapObjects);
        this.mapSize = new Dimension(mapObjects.length, mapObjects[0].length);
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
        for (int i = 0; i < this.mapObjects.length; i++) {
            for (int j = 0; j < this.mapObjects[0].length; j++) {
                mapAsString += mapObjects[i][j].toString().substring(0,1) + "  ";
            }
            mapAsString += "\n";
        }
        return mapAsString;
    }
}
