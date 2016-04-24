package edu.chalmers.vaporwave.model;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.BlastFinishedEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.gameObjects.*;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.PowerUpLoader;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.awt.*;
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

        GameEventBus.getInstance().register(this);

        this.arenaMap = arenaMap;

        this.width = arenaMap.getMapSize().width;
        this.height = arenaMap.getMapSize().height;
        this.arenaTiles = new StaticTile[width][height];

        this.arenaMovables = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                arenaTiles[i][j] = createMapObject(arenaMap.getMapObjects()[i][j]);
            }
        }
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

    public void setTile(StaticTile tile, Point position) throws ArrayIndexOutOfBoundsException {
        setTile(tile, position.x, position.y);
    }

    public void removeTile(Point position) throws ArrayIndexOutOfBoundsException {
        setTile(null, position.x, position.y);
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
     * @return appropriate Tile
     */
    public StaticTile createMapObject(MapObject mapObject) {
        switch(mapObject) {
            case DESTRUCTIBLE_WALL:
                return new DestructibleWall();
            case INDESTRUCTIBLE_WALL:
                return new IndestructibleWall();
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

    @Subscribe
    public void removeDestroyedWalls(BlastFinishedEvent blastFinishedEvent) {
        for (Point position : blastFinishedEvent.getDestroyedWalls()) {
            this.removeTile(position);
//            this.setTile(new TestPowerUp(), position);
        }
    }
}
