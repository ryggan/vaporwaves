package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.MapObject;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class Map {
    private String name;
    private MapObject[][] mapObjects;

    public Map(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = mapObjects;
    }

    public String getName() {
        return this.name;
    }

    public MapObject[][] {
        return this.mapObjects;
    }
}
