package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.MapObject;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class ArenaMap {
    private String name;
    private MapObject[][] mapObjects;

    public ArenaMap(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = mapObjects;
    }

    public String getName() {
        return this.name;
    }

    public MapObject[][] getMapObjects() {
        return this.mapObjects;
    }
}
