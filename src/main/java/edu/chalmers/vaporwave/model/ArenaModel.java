package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.MapObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andreascarlsson on 2016-04-18.
 *
 * Contains all arena objects
 *
 */
public class ArenaModel {

    private ArenaMap arenaMap;
    private StaticTile[][] arenaTiles;
    private ArrayList<Movable> arenaMovables;
    private int width;
    private int height;

    public ArenaModel(ArenaMap arenaMap) {
        this.arenaMap = arenaMap;

        newArena(arenaMap.getMapSize().width, arenaMap.getMapSize().width);
    }

    public ArenaModel(int width, int height) {
        newArena(width, height);
    }
    public ArenaModel() {
        newArena(0, 0);
    }

    public void newArena(int width, int height) {
//        this.arena = new ArrayList[width][height];
        this.arenaTiles = new StaticTile[width][height];
        this.arenaMovables = new ArrayList<>();
        this.width = width;
        this.height = height;

//        for (int i = 0; i < arena.length; i++) {
//            for (int j = 0; j < arena[0].length; j++) {
//                arena[i][j] = new ArrayList<Tile>();
//            }
//        }
    }

    public StaticTile[][] getArenaTiles() {
        return arenaTiles;
    }

    public ArrayList<Movable> getArenaMovables() {
        return arenaMovables;
    }

    public void setTile(StaticTile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= width && posy <= height && posx >= 0 && posy >= 0) {
            arenaTiles[posx][posy] = tile;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

//    public ArrayList<Tile> getTiles(int posx, int posy) {
//        return arena[posx][posy];
//    }

    public void addMovable(Movable movable) {
        arenaMovables.add(movable);
    }

    public void removeMovable(Movable movable) {
        arenaMovables.remove(movable);
    }

//    public boolean isEmpty(int posx, int posy) {
//        return (arena[posx][posy].size() == 0);
//    }

    /**
     * Iterates through our mapMatrix and calls on createObject to create the appropriate object
     * @param arenaMap going to be changed to String[][]
     * @throws Exception
     */
//    public void loadObjectsToMap(ArenaMap arenaMap) throws Exception {
//        //Första raden skall göras någon annanstans, behövs även då byta args till en String[][]
//       String[][] mapMatrix = mapFileReader.createMapArray(mapFile);
//        for(int i = 0; i < mapMatrix.length; i++) {
//            for(int j = 0; i < mapMatrix[i].length; j++) {
////               arena[i][j].add(createObject(mapMatrix[i][j], i, j));
//            }
//        }
//    }

    /**
     *
     * @param mapObject
     * @param x grid position in rows
     * @param y grid position in columns
     * @return appropriate Tile
     */
    public StaticTile createObject(MapObject mapObject, int x, int y) {
        //ArrayList<Tile> objectList = new ArrayList<Tile>();

        switch(mapObject) {
            case DESTRUCTIBLE_WALL:
//                return new DestructibleWall();
            case INDESTRUCTIBLE_WALL:
//                return new IndestructibleWall();
            default:
                return null;
        }
    }

    public String toString() {
        String temporaryString = "";
        for(int i = 0; i < arenaTiles[0].length; i++) {
            for(int j = 0; j < arenaTiles.length; j++) {

            }
        }
        return temporaryString;
    }
}
