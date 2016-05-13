package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapObject;
import javafx.geometry.Dimension2D;

import java.awt.Dimension;

public class ArenaMap {
    private String name;
    private MapObject[][] mapObjects;
    private Dimension mapSize;

    public ArenaMap(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = mapObjects;
        this.mapSize = new Dimension(mapObjects.length, mapObjects[0].length);
    }

    public String getName() {
        return this.name;
    }

    public MapObject[][] getMapObjects() {
        return ArrayCloner.mapObjectMatriceCloner(this.mapObjects);
    }

    public Dimension getMapSize() {
        return this.mapSize;
    }
}
