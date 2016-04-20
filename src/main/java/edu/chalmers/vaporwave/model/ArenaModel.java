package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.MapReader;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by andreascarlsson on 2016-04-18.
 *
 * Contains all arena objects
 *
 */
public class ArenaModel {

    private ArrayList<Tile>[][] arena;
    private int width;
    private int height;

    private MapReader mapReader;
    private File mapFile;

    public ArenaModel(int width, int height) {
        newArena(width, height);
    }
    public ArenaModel() {
        newArena(0, 0);
    }

    public void newArena(int width, int height) {
        this.arena = new ArrayList[width][height];
        this.width = width;
        this.height = height;

        for (int i = 0; i < arena.length; i++) {
            for (int j = 0; j < arena[0].length; j++) {
                arena[i][j] = new ArrayList<Tile>();
            }
        }
    }

    public ArrayList<Tile>[][] getArena() {
        return arena;
    }

    public void setTile(Tile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= width && posy <= height && posx >= 0 && posy >= 0) {
            arena[posx][posy].add(tile);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public ArrayList<Tile> getTiles(int posx, int posy) {
        return arena[posx][posy];
    }

    public boolean removeTile(Tile tile, int posx, int posy) {
        if (arena[posx][posy].contains(tile)) {
            arena[posx][posy].remove(tile);
            return true;
        }
        return false;
    }

    public boolean isEmpty(int posx, int posy) {
        return (arena[posx][posy].size() == 0);
    }

    /**
     * Iterates through our mapMatrix and calls on createObject to create the appropriate object
     * @param mapFile going to be changed to String[][]
     * @throws Exception
     */
    public void loadObjectsToMap(File mapFile) throws Exception {
        //Första raden skall göras någon annanstans, behövs även då byta args till en String[][]
       String[][] mapMatrix = mapReader.createMapArray(mapFile);
        for(int i = 0; i < mapMatrix.length; i++) {
            for(int j = 0; i < mapMatrix[i].length; j++) {
               arena[i][j].add(createObject(mapMatrix[i][j], i, j));
            }
        }
    }

    /**
     *
     * @param character
     * @param x grid position in rows
     * @param y grid position in columns
     * @return appropriate Tile
     */
    public Tile createObject(String character, int x, int y) {
        ArrayList<Tile> objectList = new ArrayList<Tile>();
        if(character.equals("O")) {
            return new DestructibleWall(x, y);
        } else if(character.equals("X")) {
            return new IndestructibleWall(x, y);
        } else if(character.equals("C")) {
            return new GameCharacter(x, y);
        } else if(character.equals("A")) {
            return new Enemy(x, y);
        } else {
            return null;
        }
    }
}
