package edu.chalmers.vaporwave.model;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.RemoveTileEvent;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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

    public void updateBombs(double timeSinceStart) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (arenaTiles[i][j] instanceof Bomb) {
                    ((Bomb)arenaTiles[i][j]).updateTimer(timeSinceStart);
                }
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

    public StaticTile getArenaTile(Point position) {
        return this.arenaTiles[position.x][position.y];
    }

    // Simple removeTile()-method that allways clear a tile on the board
    public void removeTile(Point position) {
        setTile(null, position.x, position.y);
    }

    // Specific removeTile()-method that only checks for a specific tile and removes that one, if found, else nothing happens
    public void removeTile(Point position, StaticTile tile) {
        if (getArenaTile(position) != null) {
            if (getArenaTile(position).equals(tile)) {
                removeTile(position);
            } else if (getArenaTile(position) instanceof DoubleTile) {
                setTile(removeDoubleTile((DoubleTile) getArenaTile(position), tile), position);
            }
        }
    }

    // Special recursive removeTile-method that goes through the hierarchy of a DoubbleTile-tree and removes the
    // specific tile, and IF it is found, it works its way backwards and removes DoubleTiles where necessary
    public StaticTile removeDoubleTile(DoubleTile doubleTile, StaticTile tile) {
        if (doubleTile.getLowerTile() != null) {
            if (doubleTile.getLowerTile().equals(tile)) {
                doubleTile.setLowerTile(null);
            } else if (doubleTile.getLowerTile() instanceof DoubleTile) {
                doubleTile.setLowerTile(removeDoubleTile((DoubleTile) doubleTile.getLowerTile(), tile));
            }
        }
        if (doubleTile.getUpperTile() != null) {
            if (doubleTile.getUpperTile().equals(tile)) {
                doubleTile.setUpperTile(null);
            } else if (doubleTile.getUpperTile() instanceof DoubleTile) {
                doubleTile.setUpperTile(removeDoubleTile((DoubleTile) doubleTile.getUpperTile(), tile));
            }
        }
        if (doubleTile.getLowerTile() == null && doubleTile.getUpperTile() != null) {
            return doubleTile.getUpperTile();
        } else if (doubleTile.getLowerTile() != null && doubleTile.getUpperTile() == null) {
            return doubleTile.getLowerTile();
        } else if (doubleTile.getLowerTile() == null && doubleTile.getUpperTile() == null) {
            return null;
        } else {
            return doubleTile;
        }
    }

    public void addMovable(Movable movable) {
        arenaMovables.add(movable);
    }

    public void removeMovable(Movable movable) {
        arenaMovables.remove(movable);
    }

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


    public StatPowerUp spawnStatPowerUp(java.util.List<PowerUpState> enabledPowerUpList) {
        Random randomGenerator = new Random();
        if(randomGenerator.nextInt(4) < 2) {
            return new StatPowerUp(enabledPowerUpList);
        }
        return null;
    }

    // RemoveTileEvent is posted from renderAnimatedTile() in ArenaView, when an animation is finished
    @Subscribe
    public void removeTileEventCatcher(RemoveTileEvent removeTileEvent) {
        removeTile(removeTileEvent.getGridPosition(), removeTileEvent.getTile());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
