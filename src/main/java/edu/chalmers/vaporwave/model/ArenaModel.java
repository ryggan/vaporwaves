package edu.chalmers.vaporwave.model;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.game.*;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.PowerUpType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This should act as a gateway for all the smaller parts of the arenas model,
 * and therefore holds the map and all the lists of objects on screen, as well as
 * some methods that has to do with these.
 */
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

    // The following fills the two dimensional array that contains the arenas StaticTiles,
    // with objects according to the ArenaMap
    public void initArenaMap(ArenaMap arenaMap) {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                this.arenaTiles[i][j] = createMapObject(arenaMap.getMapObjects()[i][j]);
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

    // Returns a copy of the two-dimensional array arenaTiles
    public StaticTile[][] getArenaTiles() {
        StaticTile[][] newArenaTiles = new StaticTile[this.arenaTiles.length][this.arenaTiles[0].length];
        for(int i = 0; i < this.arenaTiles.length; i++) {
            for (int j = 0; j < this.arenaTiles[0].length; j++) {
                newArenaTiles[i][j] = this.arenaTiles[i][j];
            }
        }
        return newArenaTiles;
    }

    // Returns whatever tile is put in the given position in the arena
    public StaticTile getArenaTile(Point position) {
        return this.arenaTiles[position.x][position.y];
    }

    // Here are som setters for StaticTiles; the latter ones does not replace the previous tile,
    // but stacks them in DoubleTiles instead
    public void setTile(StaticTile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= this.gridWidth && posy <= this.gridHeight && posx >= 0 && posy >= 0) {
            this.arenaTiles[posx][posy] = tile;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setTile(StaticTile tile, Point position) throws ArrayIndexOutOfBoundsException {
        setTile(tile, position.x, position.y);
    }

    public void setDoubleTile(StaticTile tile, int posx, int posy) throws ArrayIndexOutOfBoundsException {
        if (posx <= this.gridWidth && posy <= this.gridHeight && posx >= 0 && posy >= 0) {
            if (this.arenaTiles[posx][posy] == null) {
                setTile(tile, posx, posy);
            } else {
                StaticTile doubleTile = new DoubleTile(tile, this.arenaTiles[posx][posy]);
                setTile(doubleTile, posx, posy);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setDoubleTile(StaticTile tile, Point position) throws ArrayIndexOutOfBoundsException {
        setDoubleTile(tile, position.x, position.y);
    }

    // Simple removeTile()-method that always clear a tile on the board
    public void removeTile(Point position) {
        setTile(null, position.x, position.y);
    }

    // Specific removeTile()-method that only checks for a specific tile and removes that one, if found, or nothing happens
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
    // specific tile, and IF it is found, it works its way backwards recursively and removes DoubleTiles where necessary
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
        // This last returns what is left after removals above, so that a recursive loop can work
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

    // Removes all tiles, as the name implies
    public void clearTiles() {
        for (int j = 0; j < this.arenaTiles.length; j++) {
            for (int k = 0; k < this.arenaTiles[0].length; k++) {
                this.arenaTiles[j][k] = null;
            }
        }
    }

    // Returns a copy of the list arenaMovables
    public List<Movable> getArenaMovables() {
        List<Movable> newArenaMovables = new ArrayList<>();
        newArenaMovables.addAll(this.arenaMovables);
        return newArenaMovables;
    }

    // Adds a new Movable to the list
    public void addMovable(Movable movable) {
        this.arenaMovables.add(movable);
    }

    public void removeMovable(Movable movable) {
        this.arenaMovables.remove(movable);
    }

    // Every bomb on screen has a timer, and that timer needs to be updated every frame
    public void updateBombs(double timeSinceStart) {
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                if (this.arenaTiles[i][j] instanceof Bomb) {
                    ((Bomb) this.arenaTiles[i][j]).updateTimer(timeSinceStart);
                }
            }
        }
    }

    // Spawns a powerup at a random chance
    public PowerUp spawnPowerUp(java.util.List<PowerUpType> enabledPowerUpList) {
        Random randomGenerator = new Random();
        if (randomGenerator.nextDouble() < Constants.DEFAULT_POWERUP_SPAWN_CHANCE) {
            return new PowerUp(enabledPowerUpList);
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
    // (timed around 2000 nanoseconds with 11 movables on screen at the same time, performance should be ok)
    public void sortMovables() {
        int index = this.arenaMovables.size() - 1;
        for (int i = index; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.arenaMovables.get(j).getCanvasPositionY() > this.arenaMovables.get(j + 1).getCanvasPositionY()) {
                    Movable movable = this.arenaMovables.get(j);
                    this.arenaMovables.set(j, this.arenaMovables.get(j + 1));
                    this.arenaMovables.set(j + 1, movable);
                }
            }
        }
    }

    public String toString() {
        String temporaryString = "";
        for(int i = 0; i < this.arenaTiles[0].length; i++) {
            for(int j = 0; j < this.arenaTiles.length; j++) {

            }
        }
        return temporaryString;
    }
}
