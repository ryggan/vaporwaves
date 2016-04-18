package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.model.gameObjects.Tile;

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

    public ArenaModel(int width, int height) {
        newArena(width, height);
    }
    public ArenaModel() {
        newArena(0, 0);
    }

    public void newArena(int width, int height) {
        arena = new ArrayList[width][height];

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
        if (arena.length <= posx && arena[0].length <= posy && posx >= 0 && posy >= 0) {
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
}
