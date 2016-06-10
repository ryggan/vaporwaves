package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.ClonerUtility;
import edu.chalmers.vaporwave.util.MapObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a simple class that holds initial map information about one arena; where the
 * different objects are at the beginning of a game.
 */
public class ArenaMap {
    private String name;
    private MapObject[][] mapObjects;
    private Dimension mapSize;
    private Map<MapObject, Point> playersPositions;

    public ArenaMap () {
        // Only used by RandomArenaMap
    }

    public ArenaMap(String name, MapObject[][] mapObjects) {
        setNewMap(name, mapObjects);
    }

    public void setNewMap(String name, MapObject[][] mapObjects) {
        this.name = name;
        this.mapObjects = ClonerUtility.mapObjectMatrixCloner(mapObjects);
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
                    default:
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
        return ClonerUtility.mapObjectMatrixCloner(this.mapObjects);
    }

    public Dimension getMapSize() {
        return this.mapSize;
    }

    public String toString() {
        StringBuffer mapAsString = new StringBuffer();
        mapAsString.append("\nArenaMap: "+this.name+"\n");
        for (int i = 0; i < this.mapObjects[0].length; i++) {
            for (int j = 0; j < this.mapObjects.length; j++) {
                mapAsString.append(mapObjects[j][i].toString().substring(0,1) + "  ");
            }
            mapAsString.append("\n");
        }
        return mapAsString.toString();
    }
}
