package edu.chalmers.vaporwave.model;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.RemoveTileEvent;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaModel {

    private StaticTile[][] arenaTiles;
    private List<Movable> arenaMovables;
    private int gridWidth;
    private int gridHeight;

    public ArenaModel(ArenaMap arenaMap) {

        GameEventBus.getInstance().register(this);
        this.gridWidth = arenaMap.getMapSize().width;
        this.gridHeight = arenaMap.getMapSize().height;
        this.arenaTiles = new StaticTile[this.gridWidth][this.gridHeight];
        this.arenaMovables = new ArrayList<>();

        initArenaMap(arenaMap);
    }

    public void updateBombs(double timeSinceStart) {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                if (arenaTiles[i][j] instanceof Bomb) {
                    ((Bomb)arenaTiles[i][j]).updateTimer(timeSinceStart);
                }
            }
        }
    }

    public StaticTile[][] getArenaTiles() {
//        return (ArrayCloner.staticTileMatrixCloner(this.arenaTiles));
//        return ((StaticTile[][]) ArrayCloner.objectMatrixCloner(this.arenaTiles));
        StaticTile[][] newArenaTiles = new StaticTile[this.arenaTiles.length][this.arenaTiles[0].length];
        for(int i = 0; i < this.arenaTiles.length; i++) {
            for (int j = 0; j < this.arenaTiles[0].length; j++) {
                newArenaTiles[i][j] = this.arenaTiles[i][j];
            }
        }
        return newArenaTiles;
    }

    public List<Movable> getArenaMovables() {
        return arenaMovables;
    }

    public void setTile(StaticTile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= this.gridWidth && posy <= this.gridHeight && posx >= 0 && posy >= 0) {
            arenaTiles[posx][posy] = tile;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setTile(StaticTile tile, Point position) throws ArrayIndexOutOfBoundsException {
        setTile(tile, position.x, position.y);
    }

    public void setDoubleTile(StaticTile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= this.gridWidth && posy <= this.gridHeight && posx >= 0 && posy >= 0) {
            if (arenaTiles[posx][posy] == null) {
                setTile(tile, posx, posy);
            } else {
                StaticTile doubleTile = new DoubleTile(tile, arenaTiles[posx][posy]);
                setTile(doubleTile, posx, posy);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setDoubleTile(StaticTile tile, Point position) throws ArrayIndexOutOfBoundsException {
        setDoubleTile(tile, position.x, position.y);
    }

    public StaticTile getArenaTile(Point position) {
        return this.arenaTiles[position.x][position.y];
    }

    // Simple removeTile()-method that always clear a tile on the board
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

    // Special recursive removeTile-method that goes through the hierarchy of a DoubleTile-tree and removes the
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

    public void clearTiles() {
        for (int j = 0; j < this.arenaTiles.length; j++) {
            for (int k = 0; k < this.arenaTiles[0].length; k++) {
                this.arenaTiles[j][k] = null;
            }
        }
    }

    public void addMovable(Movable movable) {
        arenaMovables.add(movable);
    }

    public void removeMovable(Movable movable) {
        arenaMovables.remove(movable);
    }

    public void initArenaMap(ArenaMap arenaMap) {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                arenaTiles[i][j] = createMapObject(arenaMap.getMapObjects()[i][j]);
            }
        }
    }

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


    public StatPowerUp spawnStatPowerUp(java.util.List<PowerUpType> enabledPowerUpList) {
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

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    // Ye good olde Bubblesort, so that every movable is behind or in front of other movables in a correct way.
    // (timed around 2000 nanoseconds with 11 movables on screen at the same time, should be ok)
    public void sortMovables() {
        int index = arenaMovables.size() - 1;
        for (int i = index; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arenaMovables.get(j).getCanvasPositionY() > arenaMovables.get(j + 1).getCanvasPositionY()) {
                    Movable movable = arenaMovables.get(j);
                    arenaMovables.set(j, arenaMovables.get(j + 1));
                    arenaMovables.set(j + 1, movable);
                }
            }
        }
    }
}
